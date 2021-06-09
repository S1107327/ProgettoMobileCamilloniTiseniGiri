package com.example.progettomobilecamillonitisenigiri.Main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Adapters.CorsoAdapter
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection

class FragmentRicerca : Fragment(R.layout.fragment_ricerca), CorsoAdapter.OnCorsoListener {

    val args: FragmentRicercaArgs by navArgs()
    val firebaseConnection:FirebaseConnection by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = args.query
        view.findViewById<TextView>(R.id.risRicerca).text = query
        val rvSearch = view.findViewById<RecyclerView>(R.id.risultatiSearch)
        rvSearch.layoutManager = GridLayoutManager(context, 2)
        firebaseConnection.getListaCorsi().observe(viewLifecycleOwner, Observer<List<Corso>> { corsi->
            rvSearch.adapter = CorsoAdapter(corsi.filter { corso -> corso.titolo.contains(query,true) || corso.descrizione.contains(query,true) },this)
        })
    }
    override fun onCorsoClick(position: Int, view: View?) {
        val intent = Intent(context, CorsoActivity::class.java)
        intent.putExtra("ID_CORSO",view?.findViewById<TextView>(R.id.corsoId)!!.text)
        startActivity(intent)
    }
}