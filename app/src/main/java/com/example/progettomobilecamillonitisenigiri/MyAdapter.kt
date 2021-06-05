package com.example.progettomobilecamillonitisenigiri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Model.Corso

class MyAdapter (val data: List<Corso>, val monPopularAdapter: OnMyAdapterListener): RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>() {
    class MyAdapterViewHolder(val box: View,val onMyAdapterListener: OnMyAdapterListener) : RecyclerView.ViewHolder(box) , View.OnClickListener {
        init{
            box.setOnClickListener(this)
        }
        val cardpopular = box.findViewById<CardView>(R.id.cardpopular)
        override fun onClick(v: View?) {
            onMyAdapterListener.onCorsoClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyAdapterViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_corso,
            parent, false
        )
        return MyAdapterViewHolder(layout, monPopularAdapter)
    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.cardpopular.findViewById<TextView>(R.id.textView3).text =
            data.get(position).descrizione
    }

    override fun getItemCount(): Int = data.size

    interface OnMyAdapterListener{
        fun onCorsoClick(position: Int)
    }
}