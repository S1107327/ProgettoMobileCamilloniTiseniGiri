package com.example.progettomobilecamillonitisenigiri.Corso

import android.R.attr.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.MyAdapter
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection


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

        val model: FirebaseConnection by viewModels()
        rvLezioni.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        model.getListaLezioni().observe(viewLifecycleOwner, Observer<HashMap<String,ArrayList<Lezione>>>{ lezioni->
            rvLezioni.adapter = LezioniAdapter(corsi,this)
        })
    }


        override fun onLezioneClick(position: Int, view: View?) {
            val image = view?.findViewById<ImageView>(R.id.frecciaLezione)

            val layout = view?.findViewById<ViewGroup>(R.id.expandableLayout)
            if (layout?.visibility == View.GONE) {
                image?.rotation = 90f
                layout?.setVisibility(View.VISIBLE)
            }
            else {
                image?.rotation = 0f
                layout?.setVisibility(View.GONE)
            }
        }
}