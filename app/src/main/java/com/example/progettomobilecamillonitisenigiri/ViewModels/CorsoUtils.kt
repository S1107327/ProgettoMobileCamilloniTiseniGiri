package com.example.progettomobilecamillonitisenigiri.ViewModels

import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.google.firebase.database.DataSnapshot

class CorsoUtils {

    //Liste di appoggio per popolare  i live data
    private val lista_corsi = ArrayList<Corso>()
    private val lista_lezioni = HashMap<String, ArrayList<Lezione>>()
    private val lista_cat = HashSet<String>()
    private val lista_dispense = HashMap<String, ArrayList<Documento>>()

    init {
        lista_corsi.clear()
        lista_cat.clear()
        lista_dispense.clear()
        lista_lezioni.clear()
    }

    fun readData(snapshot: DataSnapshot) {
        if (snapshot.child("Corsi")!!.exists()) {
            for (e in snapshot.child("Corsi").children) {

                //Aggiunta corso a lista corsi
                val corso = e.getValue(Corso::class.java)
                lista_corsi.add(corso!!)

                //Aggiungo categoria del corso a un Set
                val cat = e.child("categoria").value.toString()
                lista_cat.add(cat)

                //liste di appoggio temporanee per lezioni e dispense
                val tmp_list_lezioni = ArrayList<Lezione>()
                val tmp_list_dispense = ArrayList<Documento>()

                //Aggiunta lezioni relative al corso
                for (lezione in e.child("lezioni").children) {
                    val l = lezione.getValue(Lezione::class.java)
                    if (l != null) tmp_list_lezioni.add(l)
                }
                lista_lezioni.put(corso.id, tmp_list_lezioni)

                //Aggiunta Documenti relative al corso
                for (documento in e.child("dispense").children) {
                    val l = documento.getValue(Documento::class.java)
                    if (l != null) tmp_list_dispense.add(l)
                }
                lista_dispense.put(corso.id, tmp_list_dispense)

            }
        }
    }

    fun getCorsi(): ArrayList<Corso> {
        return lista_corsi
    }

    fun getLezioni(): HashMap<String, ArrayList<Lezione>> {
        return lista_lezioni
    }

    fun getDispense(): HashMap<String, ArrayList<Documento>> {
        return lista_dispense
    }

    fun getCat(): HashSet<String> {
        return lista_cat
    }


}