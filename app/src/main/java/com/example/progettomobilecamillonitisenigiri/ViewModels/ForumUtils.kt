package com.example.progettomobilecamillonitisenigiri.ViewModels

import android.util.Log
import com.example.progettomobilecamillonitisenigiri.Model.DomandaForum
import com.google.firebase.database.DataSnapshot

class ForumUtils {
    private val mapDomande = HashMap<String,ArrayList<DomandaForum>>()

    init{
        mapDomande.clear()
    }

    fun readData(snapshot: DataSnapshot){
        mapDomande.clear()
        if(snapshot.exists()) {
            for (e in snapshot.children) {
                val domande = ArrayList<DomandaForum>()
                for (dom in e.children)
                    domande.add(dom.getValue(DomandaForum::class.java)!!)
                Log.d("prova",e.key!!)
                mapDomande.put(e.key!!, domande)
            }
        }
    }
    fun getDomande(): HashMap<String,ArrayList<DomandaForum>>{
        return mapDomande
    }
}