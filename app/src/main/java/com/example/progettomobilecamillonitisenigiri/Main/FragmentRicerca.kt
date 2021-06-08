package com.example.progettomobilecamillonitisenigiri.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.progettomobilecamillonitisenigiri.R

class FragmentRicerca : Fragment(R.layout.fragment_ricerca) {

    val args: FragmentRicercaArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = args.query
        view.findViewById<TextView>(R.id.textView4).text = query
    }
}