package com.example.progettomobilecamillonitisenigiri.Corso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.DispenseAdapter
import com.example.progettomobilecamillonitisenigiri.LezioniAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection

class FragmentDispenseCorso : Fragment(), DispenseAdapter.OnDispenseAdapterListener {
    val model: FirebaseConnection by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dispense_corso, container, false)
    }
    override fun onResume() {
        super.onResume()
        val id = requireActivity().intent.getStringExtra("ID_CORSO").toString()
        val rvDispense: RecyclerView? = view?.findViewById(R.id.recyclerViewDispense)

        rvDispense?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        model.getListaDispense().observe(viewLifecycleOwner, Observer<HashMap<String,ArrayList<Documento>>>{ dispense->
            rvDispense?.adapter = DispenseAdapter(dispense.getValue(id).toList(), this)
        })
    }

    override fun onDispenseClick(position: Int, view: View?) {
        //TODO mettere che ti apre il documento su internet
    }
}