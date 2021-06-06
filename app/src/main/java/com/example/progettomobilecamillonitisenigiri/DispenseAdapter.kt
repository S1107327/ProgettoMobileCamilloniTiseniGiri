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
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.URL

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispenseAdapter.DispenseAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_documento,
            parent, false
        )
        return DispenseAdapterViewHolder(layout, monDispenseAdapter)
    }

    override fun onBindViewHolder(holder: DispenseAdapterViewHolder, position: Int) {
        holder.cardDocumento.findViewById<TextView>(R.id.titoloDelDocumento).text =
            data.get(position).titolo
        // TODO() qui va messo url dispensa
    }

    override fun getItemCount(): Int = data.size

    interface OnDispenseAdapterListener{
        fun onDispenseClick(position: Int, v: View?)
    }
}