package com.example.progettomobilecamillonitisenigiri.Main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corsi
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Adapters.PopularAdapter
import com.example.progettomobilecamillonitisenigiri.R

class FragmentYourCourses: Fragment(R.layout.fragment_personal_courses),
    PopularAdapter.OnPopularAdapterListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val corso1: Corsi = Corsi()
        val corso2: Corsi = Corsi()
        val corso3: Corsi = Corsi()
        val corso4: Corsi = Corsi()
        val corso5: Corsi = Corsi()
        val corso6: Corsi = Corsi()
        val corso7: Corsi = Corsi()
        val corso8: Corsi = Corsi()
        val corso9: Corsi = Corsi()
        val corso10: Corsi = Corsi()
        val rvYourCourses:RecyclerView = view.findViewById(R.id.recyclerViewYourCourses)
        rvYourCourses.layoutManager = GridLayoutManager(context,2)
        rvYourCourses.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5,corso6,corso7,corso8,corso9,corso10),this)
    }
    override fun onCorsoClick(position: Int) {
        val intent = Intent(context, CorsoActivity::class.java)
        startActivity(intent)
    }
}