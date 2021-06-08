package com.example.progettomobilecamillonitisenigiri.Corso

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Adapters.DispenseAdapter
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.CorsiViewModel
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection


class FragmentDispenseCorso : Fragment(), DispenseAdapter.OnDispenseAdapterListener {
    val corsoModel: CorsiViewModel by viewModels()
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

        rvDispense?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        corsoModel.getListaDispense().observe(
            viewLifecycleOwner,
            Observer<HashMap<String, ArrayList<Documento>>> { dispense ->
                rvDispense?.adapter = DispenseAdapter(dispense.getValue(id).toList(), this)
            })
    }

    override fun onDispenseClick(position: Int, view: View?) {
        val url = view?.findViewById<TextView>(R.id.urlDocumento)?.text
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url as String?))
        startActivity(browserIntent)
    }
}