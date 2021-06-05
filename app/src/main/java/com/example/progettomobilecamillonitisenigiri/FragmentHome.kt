package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection

class FragmentHome: Fragment(R.layout.fragment_home), MyAdapter.OnMyAdapterListener{
    var list=ArrayList<Corso>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvPopolari:RecyclerView = view.findViewById(R.id.recyclerViewPopolari)
        val rvConsigliati:RecyclerView= view.findViewById(R.id.recyclerViewConsigliati)
        val rvRecenti:RecyclerView = view.findViewById(R.id.recyclerViewRecenti)
        val firebaseConnection = FirebaseConnection()
        firebaseConnection.readData() {
            rvPopolari.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            rvConsigliati?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            rvRecenti.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        }

        list = firebaseConnection.getListaCorsi()


        rvPopolari.adapter = MyAdapter(list,this)


        rvConsigliati?.adapter = MyAdapter(list,this)


        rvRecenti.adapter = MyAdapter(firebaseConnection.getListaCorsi(),this)
    }
    override fun onCorsoClick(position: Int) {
        val intent = Intent(context,CorsoActivity::class.java)
        startActivity(intent)
    }
}