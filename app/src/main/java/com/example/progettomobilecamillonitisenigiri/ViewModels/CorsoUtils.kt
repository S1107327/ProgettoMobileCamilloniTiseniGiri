package com.example.progettomobilecamillonitisenigiri.ViewModels

import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.DomandaForum
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.google.firebase.database.DataSnapshot

class CorsoUtils {

    //Liste di appoggio per popolare  i live data
    private var lista_corsi = ArrayList<Corso>()
    private val lista_lezioni = HashMap<String, ArrayList<Lezione>>()
    private val lista_cat = HashSet<String>()
    private val lista_dispense = HashMap<String, ArrayList<Documento>>()
    private val lista_CorsiPerCat = HashMap<String,ArrayList<Corso>>()
    private val mapDomande = HashMap<String,ArrayList<DomandaForum>>()

    init {
        lista_corsi.clear()
        lista_cat.clear()
        lista_dispense.clear()
        lista_lezioni.clear()
        lista_CorsiPerCat.clear()
        mapDomande.clear()
    }

    fun readData(snapshot: DataSnapshot) {
        lista_corsi.clear()
        lista_cat.clear()
        lista_dispense.clear()
        lista_lezioni.clear()
        lista_CorsiPerCat.clear()
        mapDomande.clear()
        if (snapshot.child("Corsi")!!.exists()) {
            for (e in snapshot.child("Corsi").children) {

                //Aggiunta corso a lista corsi
                val corso = e.getValue(Corso::class.java)
                lista_corsi.add(corso!!)

                //Aggiungo categoria del corso a un Set
                /*val cat = e.child("categoria").value.toString()*/
                lista_cat.add(corso.categoria)

                //liste di appoggio temporanee per lezioni e dispense
                /*val tmp_list_lezioni = ArrayList<Lezione>()
                val tmp_list_dispense = ArrayList<Documento>()
                val tmp_list_domande = ArrayList<DomandaForum>()*/

                //Aggiunta lezioni relative al corso
                /*for (lezione in e.child("lezioni").children) {
                    val l = lezione.getValue(Lezione::class.java)
                    if (l != null) tmp_list_lezioni.add(l)
                }*/
                lista_lezioni.put(corso.id, corso.lezioni)

                //Aggiunta Documenti relative al corso
                /*for (documento in e.child("dispense").children) {
                    val l = documento.getValue(Documento::class.java)
                    if (l != null) tmp_list_dispense.add(l)
                }*/
                lista_dispense.put(corso.id, corso.dispense)
                /*for(domanda in e.child("Forum").children){
                    val domForum = domanda.getValue(DomandaForum::class.java)
                    if(domForum != null) tmp_list_domande.add(domForum)
                }*/
                mapDomande.put(corso.id,corso.forum)
            }
        }
    }
    fun setCorsi(corsi : ArrayList<Corso>){
        lista_corsi = corsi
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
    fun getCorsiPerCat(): HashMap<String,ArrayList<Corso>>{
        for (categoria in getCat()){
            val tmp_list = ArrayList<Corso>()
            for (corso in getCorsi()) {
                if(corso.categoria.equals(categoria))
                    tmp_list.add(corso)
            }
            lista_CorsiPerCat.put(categoria,tmp_list)
        }
        return lista_CorsiPerCat
    }
    fun getDomande(): HashMap<String,ArrayList<DomandaForum>>{
        return mapDomande
    }


}