package com.example.progettomobilecamillonitisenigiri

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.URL

class LezioniAdapter (val data: List<Lezione>, val monPopularAdapter: OnLezioniAdapterListener): RecyclerView.Adapter<LezioniAdapter.LezioniAdapterViewHolder>() {
    class LezioniAdapterViewHolder(val box: View,val onLezioniAdapterListener: OnLezioniAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)
        }
        val cardLezione = box.findViewById<CardView>(R.id.cardViewLezione)
        override fun onClick(v: View?) {
            onLezioniAdapterListener.onLezioneClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LezioniAdapter.LezioniAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_lezione,
            parent, false
        )
        return LezioniAdapterViewHolder(layout, monPopularAdapter)
    }

    override fun onBindViewHolder(holder: LezioniAdapterViewHolder, position: Int) {
        holder.cardLezione.findViewById<TextView>(R.id.titoloDellaLezione).text =
            data.get(position).titolo
        holder.cardLezione.findViewById<TextView>(R.id.descrizioneLezione).text =
            data.get(position).descrizione
        // TODO() qui va messo url lezione
    }

    override fun getItemCount(): Int = data.size

    interface OnLezioniAdapterListener{
        fun onLezioneClick(position: Int)
    }
}