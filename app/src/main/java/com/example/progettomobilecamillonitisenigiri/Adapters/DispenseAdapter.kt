package com.example.progettomobilecamillonitisenigiri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.R

/*
* Adapter delle Dispense
* */

class DispenseAdapter (val data: List<Documento>, val monDispenseAdapter: OnDispenseAdapterListener): RecyclerView.Adapter<DispenseAdapter.DispenseAdapterViewHolder>() {
    class DispenseAdapterViewHolder(val box: View,val onDispenseAdapterListener: OnDispenseAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)
        }
        val cardDocumento = box.findViewById<CardView>(R.id.cardViewDocumento)
        override fun onClick(v: View?) {
            onDispenseAdapterListener.onDispenseClick(adapterPosition, v)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispenseAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_documento,
            parent, false
        )
        return DispenseAdapterViewHolder(layout, monDispenseAdapter)
    }

    override fun onBindViewHolder(holder: DispenseAdapterViewHolder, position: Int) {
        holder.cardDocumento.findViewById<TextView>(R.id.titoloDelDocumento).text =
            data.get(position).titolo
        holder.cardDocumento.findViewById<TextView>(R.id.urlDocumento).text =
            data.get(position).url
    }

    override fun getItemCount(): Int = data.size

    interface OnDispenseAdapterListener{
        fun onDispenseClick(position: Int, v: View?)
    }
}