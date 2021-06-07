package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection

class FragmentHome : Fragment(R.layout.fragment_home), MyAdapter.OnMyAdapterListener {
    var list = ArrayList<Corso>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvPopolari: RecyclerView = view.findViewById(R.id.recyclerViewPopolari)
        val rvConsigliati: RecyclerView = view.findViewById(R.id.recyclerViewConsigliati)
        val rvRecenti: RecyclerView = view.findViewById(R.id.recyclerViewRecenti)
        val model: FirebaseConnection by viewModels()

        rvPopolari.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvConsigliati?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecenti.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        model.getListaCorsi().observe(viewLifecycleOwner, Observer<List<Corso>> { corsi ->
            rvPopolari.adapter = MyAdapter(corsi, this)


            rvConsigliati?.adapter = MyAdapter(corsi, this)

            rvRecenti.adapter = MyAdapter(corsi.reversed(), this)


        })


    }

    override fun onCorsoClick(position: Int, v: View?) {
        val intent = Intent(context, CorsoActivity::class.java)
        intent.putExtra("ID_CORSO", v?.findViewById<TextView>(R.id.corsoId)?.text)
        startActivity(intent)
    }
}