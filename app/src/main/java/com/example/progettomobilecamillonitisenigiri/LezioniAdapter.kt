package com.example.progettomobilecamillonitisenigiri

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController
import java.util.*

class LezioniAdapter (val data: List<Lezione>, val monLezioniAdapter: OnLezioniAdapterListener): RecyclerView.Adapter<LezioniAdapter.LezioniAdapterViewHolder>() {
    class LezioniAdapterViewHolder(val box: View,val onLezioniAdapterListener: OnLezioniAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)
        }
        val cardLezione = box.findViewById<CardView>(R.id.cardViewDocumento)
        override fun onClick(v: View?) {
            onLezioniAdapterListener.onLezioneClick(adapterPosition, v)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LezioniAdapter.LezioniAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_lezione,
            parent, false
        )
        return LezioniAdapterViewHolder(layout, monLezioniAdapter)
    }
    lateinit var ytView : YouTubePlayerView
    override fun onBindViewHolder(holder: LezioniAdapterViewHolder, position: Int) {
        holder.cardLezione.findViewById<TextView>(R.id.titoloDelDocumento).text =
            data.get(position).titolo
        holder.cardLezione.findViewById<TextView>(R.id.descrizioneLezione).text =
            data.get(position).descrizione

        ytView = holder.cardLezione.findViewById<YouTubePlayerView>(R.id.youtubeVideoView)


        // TODO() qui va messo url lezione
    }

    override fun getItemCount(): Int = data.size

    interface OnLezioniAdapterListener{
        fun onLezioneClick(position: Int, v: View?)
    }




}

