package com.example.progettomobilecamillonitisenigiri.Corso

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Adapters.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.Model.UltimaLezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class FragmentLezioniCorso : Fragment(R.layout.fragment_lezioni_corso), LezioniAdapter.OnLezioniAdapterListener {
    //val corsiModel: CorsiViewModel by viewModels()
    val firebaseConnection: FirebaseConnection by viewModels()
    val tracker = YouTubePlayerTracker()
    override fun onResume() {
        super.onResume()
        val id = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val rvLezioni: RecyclerView? = view?.findViewById(R.id.recyclerViewLezioni)


        rvLezioni?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        populateLastLessonPlayer()

        firebaseConnection.getListaLezioni()
            .observe(viewLifecycleOwner, Observer<HashMap<String, ArrayList<Lezione>>> { lezioni ->
                rvLezioni?.adapter = LezioniAdapter(
                    lezioni?.getValue(id)?.toList() as List<Lezione>, this, view, this
                )
                if (!firebaseConnection.isIscritto(id)) {
                    view?.findViewById<ConstraintLayout>(R.id.paginaNonVisualizzabile)?.visibility =
                        VISIBLE
                    view?.findViewById<ConstraintLayout>(R.id.paginaVisualizzabileIscritti)?.visibility =
                        GONE
                }
            })
    }


    override fun onLezioneClick(position: Int, view: View?) {
        val image = view?.findViewById<ImageView>(R.id.immagineDocumento)

        val layout = view?.findViewById<ViewGroup>(R.id.expandableLayout)
        if (layout?.visibility == View.GONE) {
            image?.rotation = 90f
            layout?.setVisibility(View.VISIBLE)

        } else {
            image?.rotation = 0f
            layout?.setVisibility(View.GONE)
        }
    }

    //funzione che aggiunge l'ultima lezione al database
    fun addUltimaLezione(urlLezione: String?, idLezione: String, seconds: Float, repopulate: Boolean) {
        val idCorso = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val utente = firebaseConnection.getUser().value
        var ultimeLezioni = utente!!.ultimeLezioni
        for (lastLesson in ultimeLezioni) {
            if (lastLesson.id_corso == idCorso) {
                utente.ultimeLezioni.remove(lastLesson)
                break
            }
        }
        utente?.ultimeLezioni?.add(UltimaLezione(idCorso, urlLezione, idLezione, seconds))
        if (utente != null) {
            firebaseConnection.setUtente(utente)
        }
        if(repopulate) {
            populateLastLessonPlayerOnChange(urlLezione, idLezione, seconds)
        }
    }

    //funzione che popola il player dell'ultima lezione quando apro il fragment
    fun populateLastLessonPlayer() {
        val latestVideoView =
            view?.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(
                R.id.youtubeLastVideoView
            )
        latestVideoView?.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(lastYouTubePlayer: YouTubePlayer) {
                val id_corso = requireActivity().intent.getStringExtra("ID_CORSO").toString()
                val user = firebaseConnection.getUser().value
                val listaLezioni = firebaseConnection.getListaLezioni().value?.get(id_corso)
                var found =
                    false //variabile che serve per capire se nel database c'è un'ultima lezione per quel corso


                lastYouTubePlayer.addListener(tracker)
                for (ultimaLezione in user!!.ultimeLezioni) { //cerca nelle ultime lezioni se c'è quella con l'id del corso uguale a quello del fragment, se c'è la mette nel player dell'ultima lezione, se non la trova gli ci mette la prima lezione
                    if (ultimaLezione.id_corso == id_corso) {
                        ultimaLezione.url_lezione?.let {
                            lastYouTubePlayer.cueVideo(
                                it,
                                ultimaLezione.secondi
                            )
                        }
                        if (listaLezioni != null) {
                            for (lezione in listaLezioni)
                                if (lezione.id == ultimaLezione.id_lezione) {
                                    view?.findViewById<TextView>(R.id.titoloLezione)?.text =
                                        lezione.titolo
                                    view?.findViewById<TextView>(R.id.descrizioneLezione)?.text =
                                        lezione.descrizione
                                    view?.findViewById<TextView>(R.id.idLezioneHidden)?.text =
                                        lezione.id

                                }
                        }
                        found = true //siccome l'ultima lezione è presente metto il found a true
                        break
                    }
                }

                if (!found) { //se non c'è un'ultima lezione va a metterci la prima nel player
                    val primaLezione = listaLezioni?.get(0)
                    if (primaLezione != null) {
                        lastYouTubePlayer.cueVideo(primaLezione.url, 0f)
                        view?.findViewById<TextView>(R.id.titoloLezione)?.text = primaLezione.titolo
                        view?.findViewById<TextView>(R.id.descrizioneLezione)?.text =
                            primaLezione.descrizione
                        view?.findViewById<TextView>(R.id.idLezioneHidden)?.text =
                            primaLezione.id

                    }
                }

            }

            //quando il video viene fatto partire o messo in pausa viene messo come ultima lezione
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.PAUSED) {
                    addUltimaLezione(tracker.videoId, view?.findViewById<TextView>(R.id.idLezioneHidden)?.text.toString(), tracker.currentSecond,false)
                }
                if (state == PlayerConstants.PlayerState.PLAYING) {
                    addUltimaLezione(tracker.videoId, view?.findViewById<TextView>(R.id.idLezioneHidden)?.text.toString(), tracker.currentSecond,false)
                }
            }
        })
    }

    //funzione che popola il player dell'ultima lezione quando viene cambiata l'ultima lezione nel database
    fun populateLastLessonPlayerOnChange(urlLezione: String?, idLezione: String, seconds: Float) {
        var latestVideoView =
            view?.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(
                R.id.youtubeLastVideoView
            )
        val id_corso = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val listaLezioni = firebaseConnection.getListaLezioni().value?.get(id_corso)
        val youTubePlayerView = context?.let { YouTubePlayerView(it) }
        youTubePlayerView?.layoutParams = latestVideoView?.layoutParams
        youTubePlayerView?.id = latestVideoView?.id!!
        youTubePlayerView?.enableAutomaticInitialization = false

        youTubePlayerView?.initialize(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(lastYouTubePlayer: YouTubePlayer) {
                lastYouTubePlayer.addListener(tracker)
                if (urlLezione != null) {
                    lastYouTubePlayer.cueVideo(urlLezione, seconds)
                }
            }
            //quando il video viene fatto partire o messo in pausa viene messo come ultima lezione
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.PAUSED) {
                    addUltimaLezione(tracker.videoId, view?.findViewById<TextView>(R.id.idLezioneHidden)?.text.toString(), tracker.currentSecond,false)
                }
                if (state == PlayerConstants.PlayerState.PLAYING) {
                    addUltimaLezione(tracker.videoId, view?.findViewById<TextView>(R.id.idLezioneHidden)?.text.toString(), tracker.currentSecond,false)
                }
            }
        })
        if (listaLezioni != null) {
            for (lezione in listaLezioni) {
                if (lezione.id == idLezione) {
                    view?.findViewById<TextView>(R.id.titoloLezione)?.text =
                        lezione.titolo
                    view?.findViewById<TextView>(R.id.descrizioneLezione)?.text =
                        lezione.descrizione
                    view?.findViewById<TextView>(R.id.idLezioneHidden)?.text =
                        lezione.id
                }
            }
        }

        val parent = latestVideoView?.parent as ViewGroup
        val index = parent.indexOfChild(latestVideoView)
        parent.removeView(latestVideoView)

        parent.addView(youTubePlayerView, index)
    }
}