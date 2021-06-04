package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity

class FragmentFavorites: Fragment(R.layout.fragment_favorites),PopularAdapter.OnPopularAdapterListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val corso1:Corsi = Corsi()
        val corso2:Corsi = Corsi()
        val corso3:Corsi = Corsi()
        val corso4:Corsi = Corsi()
        val corso5:Corsi = Corsi()
        val corso6:Corsi = Corsi()
        val corso7:Corsi = Corsi()
        val corso8:Corsi = Corsi()
        val corso9:Corsi = Corsi()
        val corso10:Corsi = Corsi()
        val rvFavorites: RecyclerView = view.findViewById(R.id.recyclerViewFavorites)
        rvFavorites.layoutManager = GridLayoutManager(context,2)
        rvFavorites.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5,corso6,corso7,corso8,corso9,corso10),this)
    }

    override fun onCorsoClick(position: Int) {
        val intent = Intent(context, CorsoActivity::class.java)
        startActivity(intent)
    }
}