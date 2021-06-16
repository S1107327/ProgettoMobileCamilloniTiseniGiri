package com.example.progettomobilecamillonitisenigiri.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Model.RispostaForum
import com.example.progettomobilecamillonitisenigiri.R
/*
* Adapter delle Risposte del Forum
* */

class RispostaForumAdapter(val data: List<RispostaForum>): RecyclerView.Adapter<RispostaForumAdapter.RispostaForumViewHolder>() {
    class RispostaForumViewHolder(val box: View): RecyclerView.ViewHolder(box){
        val risposta = box.findViewById<CardView>(R.id.cardRisposte)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RispostaForumViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_risposta,
            parent, false
        )
        return RispostaForumViewHolder(layout)
    }
    override fun onBindViewHolder(holder: RispostaForumViewHolder, position: Int) {
        holder.risposta.findViewById<TextView>(R.id.nomeRisp).text = data.get(position).nomeUtente
        holder.risposta.findViewById<TextView>(R.id.cognomeRisp).text = data.get(position).cognomeUtente
        holder.risposta.findViewById<TextView>(R.id.risposta).text = data.get(position).risposta
    }
    override fun getItemCount(): Int = data.size
}