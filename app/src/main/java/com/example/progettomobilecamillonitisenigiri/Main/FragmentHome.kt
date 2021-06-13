package com.example.progettomobilecamillonitisenigiri.Main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Adapters.CorsoAdapter
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.UltimaLezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker


class FragmentHome : Fragment(R.layout.fragment_home), CorsoAdapter.OnCorsoListener {
    var list = ArrayList<Corso>()
    lateinit var mProgressbar: ProgressDialog
    var isTheFirstTime = true
    val firebaseConnection: FirebaseConnection by viewModels()
    val tracker = YouTubePlayerTracker()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvPopolari: RecyclerView = view.findViewById(R.id.recyclerViewPopolari)
        val rvConsigliati: RecyclerView = view.findViewById(R.id.recyclerViewConsigliati)
        val rvRecenti: RecyclerView = view.findViewById(R.id.recyclerViewRecenti)

        if(isTheFirstTime){
            mProgressbar = ProgressDialog(context)
            mProgressbar!!.setMessage("Sto caricando i corsi...")
            mProgressbar.show()
            isTheFirstTime = false
        }


        rvPopolari.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvConsigliati?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecenti.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvPopolari.adapter = CorsoAdapter(ArrayList<Corso>(), this)
        rvConsigliati?.adapter = CorsoAdapter(ArrayList<Corso>(), this)
        rvRecenti.adapter =CorsoAdapter(ArrayList<Corso>(), this)
        firebaseConnection.getListaCorsi().observe(
            viewLifecycleOwner,
            Observer<List<Corso>> { corsi ->
                populateLastLessonPlayer()
                rvPopolari.adapter = CorsoAdapter(corsi, this)
                rvConsigliati?.adapter = CorsoAdapter(
                    firebaseConnection.getListaConsigliati(corsi as ArrayList<Corso>),
                    this
                )
                rvRecenti.adapter = CorsoAdapter(corsi.takeLast(5), this)
                mProgressbar.hide()
            })



    }

    override fun onCorsoClick(position: Int, v: View?) {
        val intent = Intent(context, CorsoActivity::class.java)
        intent.putExtra("ID_CORSO", v?.findViewById<TextView>(R.id.corsoId)?.text)
        startActivity(intent)
    }

    //funzione che aggiunge l'ultima lezione al database
    fun addUltimaLezione(urlLezione: String?, idLezione: String, seconds: Float, idCorso: String?) {
        val utente = firebaseConnection.getUser().value
        var ultimeLezioni = utente!!.ultimeLezioni
        for (lastLesson in ultimeLezioni) {
            if (lastLesson.id_corso == idCorso) {
                utente.ultimeLezioni.remove(lastLesson)
                break
            }
        }
        utente?.ultimeLezioni?.add(
            UltimaLezione(
                utente.ultimaLezione.id_corso,
                urlLezione,
                idLezione,
                seconds
            )
        )
        utente?.ultimaLezione = UltimaLezione(
            utente.ultimaLezione.id_corso,
            urlLezione,
            idLezione,
            seconds
        )
        firebaseConnection.setUtente(utente)
    }

    fun populateLastLessonPlayer() {
        val user = firebaseConnection.getUser().value
        val latestVideoView =
            view?.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(
                R.id.videoView
            )
        user?.firstName?.let { Log.d("prova", it) }
        if(!user?.ultimaLezione?.id_corso.equals("")) {
            val titolo = view?.findViewById<TextView>(R.id.lezione)
            val card = view?.findViewById<com.google.android.material.card.MaterialCardView>(R.id.card)
            titolo?.visibility = View.VISIBLE
            card?.visibility = View.VISIBLE
            latestVideoView?.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(lastYouTubePlayer: YouTubePlayer) {
                    lastYouTubePlayer.addListener(tracker)
                    val listaLezioni =
                        firebaseConnection.getListaLezioni().value?.get(user?.ultimaLezione?.id_corso)

                    user?.ultimaLezione?.url_lezione?.let {
                        lastYouTubePlayer.cueVideo(
                            it,
                            user?.ultimaLezione?.secondi
                        )
                    }
                    if (listaLezioni != null) {
                        for (lezione in listaLezioni) {
                            if (lezione.id == user?.ultimaLezione?.id_lezione) {
                                view?.findViewById<TextView>(R.id.titoloLezione)?.text =
                                    lezione.titolo
                                view?.findViewById<TextView>(R.id.descrizioneLezione)?.text =
                                    lezione.descrizione
                                view?.findViewById<TextView>(R.id.idLezioneHidden)?.text =
                                    lezione.id
                                view?.findViewById<com.google.android.material.button.MaterialButton>(
                                    R.id.vaiAlCorso
                                )?.setOnClickListener() {
                                    val intent = Intent(context, CorsoActivity::class.java)
                                    intent.putExtra("ID_CORSO", user?.ultimaLezione?.id_corso)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }

                //quando il video viene fatto partire o messo in pausa viene messo come ultima lezione
                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    if (state == PlayerConstants.PlayerState.PAUSED) {
                        user?.ultimaLezione?.id_lezione?.let {
                            addUltimaLezione(
                                tracker.videoId,
                                it, tracker.currentSecond, user?.ultimaLezione?.id_corso
                            )
                        }
                    }
                }
            })
        }
        else{ //se non c'è un'ultima lezione toglie il player
            val titolo = view?.findViewById<TextView>(R.id.lezione)
            val card = view?.findViewById<com.google.android.material.card.MaterialCardView>(R.id.card)
            titolo?.visibility = View.GONE
            card?.visibility = View.GONE


        }
    }
}