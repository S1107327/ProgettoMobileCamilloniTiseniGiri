package com.example.progettomobilecamillonitisenigiri.Corso

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.MyAdapter
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.Model.Lezione

class FragmentLezioniCorso : Fragment(),  LezioniAdapter.OnLezioniAdapterListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lezioni_corso, container, false)
    }
        var list=ArrayList<Lezione>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvLezioni: RecyclerView = view.findViewById(R.id.recyclerViewLezioni)

        val firebaseConnection = FirebaseConnection()
        firebaseConnection.readDataLezioni() {
            rvLezioni.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        list = firebaseConnection.getListaLezioni()


        rvLezioni.adapter = LezioniAdapter(list,this)
    }


        override fun onLezioneClick(position: Int) {
            TODO()
        }
}