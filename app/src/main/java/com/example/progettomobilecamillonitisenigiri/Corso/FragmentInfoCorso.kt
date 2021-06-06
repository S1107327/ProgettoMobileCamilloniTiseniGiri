package com.example.progettomobilecamillonitisenigiri.Corso

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection
import com.squareup.picasso.Picasso
import java.lang.Exception

class FragmentInfoCorso : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id = arguments?.getString("ID_CORSO")
        if (id != null) {
            Log.d("CiaoId",id)
        }
        val model: FirebaseConnection by viewModels()
        var corso: Corso? = null
        model.getListaCorsi().observe(viewLifecycleOwner, Observer<List<Corso>>{ corsi->
            for(a in corsi)
                if (a.id.equals(id)){
                    view?.findViewById<TextView>(R.id.nomeCorso)?.text = a.titolo
                    view?.findViewById<TextView>(R.id.descrizioneCorso)?.text = a.descrizione
                    view?.findViewById<TextView>(R.id.categoriaCorso)?.text = a.categoria
                    view?.findViewById<TextView>(R.id.numLezioni)?.text = a.lezioni.size.toString()
                    try {
                        Picasso.get().load(a.immagine).into(view?.findViewById<ImageView>(R.id.imageCorso))
                    }catch (e: Exception){
                        Picasso.get().load("https://png.pngtree.com/png-vector/20191120/ourmid/pngtree-training-course-online-computer-chat-flat-color-icon-vector-png-image_2007114.jpg").into(view?.findViewById<ImageView>(R.id.imageCorso))
                    }

                }

        })


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_corso, container, false)
    }
}