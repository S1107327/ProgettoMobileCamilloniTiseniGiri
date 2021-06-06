package com.example.progettomobilecamillonitisenigiri.Utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
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
    private val listCategorie = MutableLiveData<Set<String>>()
    private val listLezioni = MutableLiveData<HashMap<String,ArrayList<Lezione>>>()
    private val listDispense = MutableLiveData<HashMap<String,ArrayList<Documento>>>()

    init {

        database = FirebaseDatabase.getInstance().reference
        readData()

    }
    val lista_corsi = ArrayList<Corso>()
    val lista_lezioni = HashMap<String,ArrayList<Lezione>>()
    val lista_cat = HashSet<String>()
    val lista_dispense = HashMap<String,ArrayList<Documento>>()

    fun readData() {

        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.child("Corsi")!!.exists()) {
                    for (e in snapshot.child("Corsi").children) {
                        val corso = e.getValue(Corso::class.java)
                        val cat = e.child("categoria").toString()
                        val tmp_list = ArrayList<Lezione>()
                        val tmp_list_dispense = ArrayList<Documento>()
                        lista_cat.add(cat)
                        lista_corsi.add(corso!!)
                        for (lezione in e.child("lezioni").children) {
                            val l = lezione.getValue(Lezione::class.java)
                            if (l != null) tmp_list.add(l)
                        }
                        lista_lezioni.put(corso.id,tmp_list)
                        for (documento in e.child("dispense").children) {
                            val l = documento.getValue(Documento::class.java)
                            if (l != null) tmp_list_dispense.add(l)
                        }
                        lista_dispense.put(corso.id,tmp_list_dispense)
                    }
                    //inserisco il valore nelle mutableLiveData
                    listLezioni.postValue(lista_lezioni)
                    listCorsi.postValue(lista_corsi)
                    listCategorie.postValue(lista_cat)
                    listDispense.postValue(lista_dispense)
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
        System.out.println("EntraLezione")
        return listLezioni
    }
    fun getCategorie(): MutableLiveData<Set<String>> {
        System.out.println("EntraCategoria")
        return listCategorie
    }
    fun getListaDispense(): MutableLiveData<HashMap<String,ArrayList<Documento>>> {
        System.out.println("EntraDispense")
        return listDispense
    }

    fun getCorso(id: String?): Corso? {

        val it = lista_corsi.iterator()
        for(corso in lista_corsi)
            if (corso.id == id)
                return corso
        return null
    }
}