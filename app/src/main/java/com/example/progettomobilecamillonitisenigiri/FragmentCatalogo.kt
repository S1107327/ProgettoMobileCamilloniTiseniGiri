package com.example.progettomobilecamillonitisenigiri

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText


class FragmentCatalogo : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_catalogo,container,false)
        val chipGroup1 = view.findViewById<ChipGroup>(R.id.chipGroup)
        val chipGroup2 = view.findViewById<ChipGroup>(R.id.chipGroup2)
        for(i in 1..12) {
            var chip = inflater.inflate(R.layout.chip_catalogo, chipGroup1, false) as Chip
            var chip2 = inflater.inflate(R.layout.chip_catalogo, chipGroup2, false) as Chip
            if(i % 2 == 0) {
                chip.id = i
                chip.text ="design"
                chip.setOnClickListener {
                    onclick(view)
                }
                chipGroup1.addView(chip)
            }
            else {
                chip2.id = i
                chip2.text ="design"
                chip2.setOnClickListener {
                    onclick(view)
                }
                chipGroup2.addView(chip2)
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val corso1:Corsi = Corsi()
        val corso2:Corsi = Corsi()
        val corso3:Corsi = Corsi()
        val corso4:Corsi = Corsi()
        val corso5:Corsi = Corsi()
        val rvCat1: RecyclerView = view.findViewById(R.id.recyclerViewCat1)
        rvCat1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCat1.adapter = PopularAdapter(
            mutableListOf<Corsi>(
                corso1,
                corso2,
                corso3,
                corso4,
                corso5
            )
        )
        val rvCat2: RecyclerView = view.findViewById(R.id.recyclerViewCat2)
        rvCat2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCat2.adapter = PopularAdapter(
            mutableListOf<Corsi>(
                corso1,
                corso2,
                corso3,
                corso4,
                corso5
            )
        )
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
    public fun onclick(view: View){
        view.findNavController().navigate(R.id.action_FragmentCatalogo_to_FragmentCategoria)
    }
}