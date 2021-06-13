package com.example.progettomobilecamillonitisenigiri.Main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
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

class FragmentCategoria: Fragment(R.layout.fragment_categoria), CorsoAdapter.OnCorsoListener {
    val firebaseConnection:FirebaseConnection by viewModels()
    val args:FragmentCategoriaArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoria = args.categoria
        val rvCategoria: RecyclerView = view.findViewById(R.id.recyclerViewCategoria)
        rvCategoria.layoutManager = GridLayoutManager(context, 2)
        rvCategoria.adapter = CorsoAdapter(ArrayList<Corso>(),this)
        firebaseConnection.getCorsiPerCat().observe(viewLifecycleOwner,
            Observer<HashMap<String, ArrayList<Corso>>> { corsiCat ->
                rvCategoria.adapter = corsiCat.get(categoria)?.let { CorsoAdapter(it,this) }
            })
    }

    override fun onCorsoClick(position: Int, view: View?) {
        val intent = Intent(context, CorsoActivity::class.java)
        intent.putExtra("ID_CORSO",view?.findViewById<TextView>(R.id.corsoId)!!.text)
        startActivity(intent)
    }
}