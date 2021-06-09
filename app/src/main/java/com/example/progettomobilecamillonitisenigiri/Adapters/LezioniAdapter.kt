package com.example.progettomobilecamillonitisenigiri.Adapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.FragmentLezioniCorso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class LezioniAdapter(
    val data: List<Lezione>,
    val monLezioniAdapter: OnLezioniAdapterListener,
    val view: View?,
    val fragment: FragmentLezioniCorso
): RecyclerView.Adapter<LezioniAdapter.LezioniAdapterViewHolder>() {
    val infoLastLesson = Bundle()
    class LezioniAdapterViewHolder(
        val box: View,
        val onLezioniAdapterListener: OnLezioniAdapterListener
    ) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)
        }
        val cardLezione = box.findViewById<CardView>(R.id.cardViewDocumento)
        override fun onClick(v: View?) {
            onLezioniAdapterListener.onLezioneClick(adapterPosition, v)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LezioniAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_lezione,
            parent, false
        )
        return LezioniAdapterViewHolder(layout, monLezioniAdapter)
    }
    lateinit var ytView : YouTubePlayerView //video player delle varie lezioni
    lateinit var latestVideoView : YouTubePlayerView //video player dell'ultima lezione vista
    override fun onBindViewHolder(holder: LezioniAdapterViewHolder, position: Int) {
        holder.cardLezione.findViewById<TextView>(R.id.titoloDelDocumento).text =
            data.get(position).titolo
        holder.cardLezione.findViewById<TextView>(R.id.descrizioneLezione).text =
            data.get(position).descrizione

        //FUNZIONALITA' PER I VIDEO
        val tracker = YouTubePlayerTracker()
        ytView = holder.cardLezione.findViewById<YouTubePlayerView>(R.id.youtubeVideoView)
        ytView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data.get(position).url
                youTubePlayer.cueVideo(videoId, 0f)
                youTubePlayer.addListener(tracker)
            }

            //quando il video viene fatto partire o messo in pausa viene messo come ultima lezione
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.PAUSED) {

                    infoLastLesson.putString("id", tracker.videoId)
                    infoLastLesson.putFloat("secondi", tracker.currentSecond)
                    fragment.prova(tracker.videoId, tracker.currentSecond)


                    /*
                    if (view != null) {
                        latestVideoView = view.findViewById(R.id.youtubeLastVideoView)
                    }
                    latestVideoView.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(lastYouTubePlayer: YouTubePlayer) {

                            val lastVideoId = tracker.videoId
                            val seconds = tracker.currentSecond
                            if (lastVideoId != null) {
                                lastYouTubePlayer.cueVideo(lastVideoId, seconds)
                            }
                        }
                    })
                    */

                }
            }
        })
    }

    override fun getItemCount(): Int = data.size

    interface OnLezioniAdapterListener{
        fun onLezioneClick(position: Int, v: View?)
    }

    fun getInfo(): Bundle{

        return infoLastLesson
    }
}

