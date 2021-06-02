package com.example.progettomobilecamillonitisenigiri

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCatalogo.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val componentName = ComponentName("com.example.progettomobilecamillonitisenigiri", "SearchableActivity")
        view.findViewById<SearchView>(R.id.searchView2).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
    }
}