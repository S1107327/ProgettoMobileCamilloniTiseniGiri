package com.example.progettomobilecamillonitisenigiri.Utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.internal.artificialFrame

class FirebaseConnection : ViewModel() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private val listCorsi = MutableLiveData<List<Corso>>()
    private val listCategorie = MutableLiveData<List<String>>()
    private val listLezioni = MutableLiveData<HashMap<String,ArrayList<Lezione>>>()

    init {

        database = FirebaseDatabase.getInstance().reference
        readData()

    }
    val lista_corsi = ArrayList<Corso>()
    val lista_lezioni = HashMap<String,ArrayList<Lezione>>()
    val tmp_list = ArrayList<Lezione>()
    val lista_cat = ArrayList<String>()

    fun readData() {

        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.child("Corsi")!!.exists()) {
                    for (e in snapshot.child("Corsi").children) {
                        tmp_list.clear()
                        val corso = e.getValue(Corso::class.java)
                        val cat = e.child("categoria").toString()
                        lista_cat.add(cat!!)
                        lista_corsi.add(corso!!)
                        for (lezione in e.child("lezioni").children) {
                            val l = lezione.getValue(Lezione::class.java)
                            if (l != null) tmp_list.add(l)
                        }
                        lista_lezioni.put(corso.id.toString(),tmp_list)
                    }
                    //inserisco il valore nelle mutableLiveData
                    listLezioni.postValue(lista_lezioni)
                    listCorsi.postValue(lista_corsi)
                    listCategorie.postValue(lista_cat)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }


    fun getListaCorsi(): MutableLiveData<List<Corso>> {
        System.out.println("Entra")
        return listCorsi
    }
    fun getListaLezioni(): MutableLiveData<HashMap<String,ArrayList<Lezione>>> {
        System.out.println("Entra")
        return listLezioni
    }
    fun getCategorie(): MutableLiveData<List<String>> {
        System.out.println("EntraCategoria")
        return listCategorie
    }

    fun getCorso(id: String?): Corso? {

        val it = lista_corsi.iterator()
        for(corso in lista_corsi)
            if (corso.id == id)
                return corso
        return null
    }
}