package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class FragmentCatalogo : Fragment(R.layout.fragment_catalogo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val corso1:Corsi = Corsi()
        val corso2:Corsi = Corsi()
        val corso3:Corsi = Corsi()
        val corso4:Corsi = Corsi()
        val corso5:Corsi = Corsi()
        val rvCat1: RecyclerView = view.findViewById(R.id.recyclerViewCat1)
        rvCat1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        rvCat1.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5))
        val rvCat2: RecyclerView = view.findViewById(R.id.recyclerViewCat2)
        rvCat2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        rvCat2.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5))
        val chip = view.findViewById<Chip>(R.id.chip1)
        chip.setOnClickListener {
            view.findNavController().navigate(R.id.action_FragmentCatalogo_to_FragmentCategoria)
        }
        val searchButton = view.findViewById<Button>(R.id.textButton)
        searchButton.setOnClickListener{
            val query = view.findViewById<TextInputEditText>(R.id.query).text.toString()
            val action = FragmentCatalogoDirections.actionFragmentCatalogoToFragmentRicerca(query)
            view.findNavController().navigate(action)
        }
    }
}