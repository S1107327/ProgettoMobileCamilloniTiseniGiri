package com.example.progettomobilecamillonitisenigiri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PopularAdapter (val data: List<Corsi>): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>(){
    class PopularViewHolder(val box: View) : RecyclerView.ViewHolder(box) {
        val cardpopular = box.findViewById<CardView>(R.id.cardpopular)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layout_corso,
            parent, false)
        return PopularViewHolder(layout)
    }
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.cardpopular.findViewById<TextView>(R.id.textView3).text = data.get(position).nomeCorso
    }
    override fun getItemCount(): Int = data.size
}