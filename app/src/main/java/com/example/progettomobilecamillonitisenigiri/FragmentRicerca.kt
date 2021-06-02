package com.example.progettomobilecamillonitisenigiri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class FragmentRicerca : Fragment(R.layout.fragment_ricerca) {

    val args:FragmentRicercaArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = args.query
        view.findViewById<TextView>(R.id.textView4).text = query
    }
}