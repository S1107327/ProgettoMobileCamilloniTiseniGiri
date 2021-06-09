package com.example.progettomobilecamillonitisenigiri.Corso

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Adapters.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.Model.UltimaLezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class FragmentLezioniCorso : Fragment(R.layout.fragment_lezioni_corso), LezioniAdapter.OnLezioniAdapterListener {
    //val corsiModel: CorsiViewModel by viewModels()
    val firebaseConnection: FirebaseConnection by viewModels()
    override fun onResume() {
        super.onResume()
        val id = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val rvLezioni: RecyclerView? = view?.findViewById(R.id.recyclerViewLezioni)


        rvLezioni?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)




        firebaseConnection.getListaLezioni()
            .observe(viewLifecycleOwner, Observer<HashMap<String, ArrayList<Lezione>>> { lezioni ->
                val adapter = LezioniAdapter(lezioni.getValue(id).toList(), this, view, this)
                rvLezioni?.adapter = adapter

                //cose per ultimo video visto
                val lastVideoId = adapter.getInfo().getString("id")
                val lastVideoSeconds = adapter.getInfo().getFloat("secondi")

                val lastVideoView = view?.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(R.id.youtubeLastVideoView)

                if(!firebaseConnection.isIscritto(id)){
                    view?.findViewById<ConstraintLayout>(R.id.paginaNonVisualizzabile)?.visibility = VISIBLE
                    view?.findViewById<ConstraintLayout>(R.id.paginaVisualizzabileIscritti)?.visibility = GONE
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

    fun prova(idLezione: String?, seconds: Float){
        val idCorso = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val utente = firebaseConnection.getUser().value
        utente?.ultimeLezioni?.add(UltimaLezione(idCorso, idLezione, seconds))
        if (utente != null) {
            firebaseConnection.setUtente(utente)
        }
    }
}