package com.example.progettomobilecamillonitisenigiri

import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentCatalogo : Fragment(), MyAdapter.OnMyAdapterListener {
    //Firebase references
    override fun onStart() {
        super.onStart()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalogo, container, false)
        val chipGroup1 = view.findViewById<ChipGroup>(R.id.chipGroupCatalogo1)
        val chipGroup2 = view.findViewById<ChipGroup>(R.id.chipGroupCatalogo2)


        for (i in 1..12) {
            var chip = inflater.inflate(R.layout.chip_catalogo, chipGroup1, false) as Chip
            var chip2 = inflater.inflate(R.layout.chip_catalogo, chipGroup2, false) as Chip
            if (i % 2 == 0) {
                chip.id = i
                chip.text = "design"
                chip.setOnClickListener {
                    onclick(view)
                }
                chipGroup1.addView(chip)
            } else {
                chip2.id = i
                chip2.text = "design"
                chip2.setOnClickListener {
                    onclick(view)
                }
                chipGroup2.addView(chip2)
            }
        }
        return view
    }


    var list = ArrayList<Corso>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvCat1: RecyclerView = view.findViewById(R.id.recyclerViewCat1)
        val rvCat2: RecyclerView = view.findViewById(R.id.recyclerViewCat2)

        rvCat1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCat2.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val model:FirebaseConnection by viewModels()
        model.getListaCorsi().observe(viewLifecycleOwner,Observer<List<Corso>>{corsi->
            rvCat1.adapter = MyAdapter(corsi, this)
            rvCat2.adapter = MyAdapter(corsi, this)
        })


        val editText = view.findViewById<TextInputEditText>(R.id.query)
        editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = editText.text.toString()
                val action = FragmentCatalogoDirections.actionFragmentCatalogoToFragmentRicerca(
                    query
                )
                view.findNavController().navigate(action)
                return@OnEditorActionListener true
            }
            false
        })
    }


    fun onclick(view: View) {
        view.findNavController().navigate(R.id.action_FragmentCatalogo_to_FragmentCategoria)
    }

    override fun onCorsoClick(position: Int,v: View?) {
        val intent = Intent(context, CorsoActivity::class.java)
        intent.putExtra("ID_CORSO", v?.findViewById<TextView>(R.id.corsoId)?.text)
        startActivity(intent)
    }


}