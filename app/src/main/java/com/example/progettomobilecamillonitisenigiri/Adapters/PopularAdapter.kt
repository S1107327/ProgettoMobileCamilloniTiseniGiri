package com.example.progettomobilecamillonitisenigiri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corsi
import com.example.progettomobilecamillonitisenigiri.R

class PopularAdapter (val data: List<Corsi>, val monPopularAdapter: OnPopularAdapterListener): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    class PopularViewHolder(val box: View,val onPopularAdapterListener: OnPopularAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
           box.setOnClickListener(this)
        }
        val cardpopular = box.findViewById<CardView>(R.id.cardpopular)
        override fun onClick(v: View?) {
            onPopularAdapterListener.onCorsoClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_corso,
            parent, false
        )
        return PopularViewHolder(layout,monPopularAdapter)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.cardpopular.findViewById<TextView>(R.id.textView3).text =
            data.get(position).nomeCorso
    }

    override fun getItemCount(): Int = data.size

    interface OnPopularAdapterListener{
        fun onCorsoClick(position: Int)
    }
}