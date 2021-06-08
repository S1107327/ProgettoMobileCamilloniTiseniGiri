package com.example.progettomobilecamillonitisenigiri.Corso

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
import com.example.progettomobilecamillonitisenigiri.Adapters.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.CorsiViewModel
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.ViewModels.UserViewModel


class FragmentLezioniCorso : Fragment(R.layout.fragment_lezioni_corso), LezioniAdapter.OnLezioniAdapterListener {
    val corsiModel: CorsiViewModel by viewModels()
    //val userModel: UserViewModel by viewModels()
    override fun onResume() {
        super.onResume()
        val id = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val rvLezioni: RecyclerView? = view?.findViewById(R.id.recyclerViewLezioni)

        rvLezioni?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        corsiModel.getListaLezioni()
            .observe(viewLifecycleOwner, Observer<HashMap<String, ArrayList<Lezione>>> { lezioni ->
                rvLezioni?.adapter = LezioniAdapter(lezioni.getValue(id).toList(), this, view)
            })
    }


    override fun onLezioneClick(position: Int, view: View?) {
        val image = view?.findViewById<ImageView>(R.id.immagineDocumento)

        val layout = view?.findViewById<ViewGroup>(R.id.expandableLayout)
        if (layout?.visibility == View.GONE) {
            image?.rotation = 90f
            layout?.setVisibility(View.VISIBLE)

        } else {
            image?.rotation = 0f
            layout?.setVisibility(View.GONE)
        }
    }
}