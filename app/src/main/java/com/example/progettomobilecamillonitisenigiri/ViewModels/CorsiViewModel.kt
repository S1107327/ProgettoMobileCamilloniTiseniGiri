package com.example.progettomobilecamillonitisenigiri.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CorsiViewModel: ViewModel() {
    //Riferimenti al Database
    private val mDatabase = FirebaseDatabase.getInstance().reference //riferimento a FireBase//riferimento al Database
    private val loggedUser = FirebaseAuth.getInstance().currentUser

    //LiveData
    private val listCorsi = MutableLiveData<List<Corso>>()
    private val listAggiuntiDiRecente = MutableLiveData<List<Corso>>()
    private val listCategorie = MutableLiveData<Set<String>>()
    private val listLezioni = MutableLiveData<HashMap<String, ArrayList<Lezione>>>()
    private val listDispense = MutableLiveData<HashMap<String, ArrayList<Documento>>>()
    private val listaConsigliati = MutableLiveData<List<Corso>>()

    //Variabili di supporto da inserire nei LiveData
    private val lista_corsi = ArrayList<Corso>()
    private val lista_lezioni = HashMap<String, ArrayList<Lezione>>()
    private val lista_cat = HashSet<String>()
    private val lista_dispense = HashMap<String, ArrayList<Documento>>()
        init{
            mDatabase.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    readCorsi(snapshot)
                    readCategorie()
                    readLezioni()
                    readDocumenti()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    //funzione che estrae i corsi dal DB e li inserisce nel LiveData.
     fun readCorsi(snapshot: DataSnapshot){
         lista_corsi.clear()
         if (snapshot.child("Corsi")!!.exists()) {
             for (e in snapshot.child("Corsi").children) {
                 val corso = e.getValue(Corso::class.java)
                 lista_corsi.add(corso!!)
             }
             listCorsi.postValue(lista_corsi)
             listAggiuntiDiRecente.postValue(lista_corsi.reversed())//per la sezione aggiunti di recente
         }
     }

    //funzione che estrae le categorie dai corsi e li inserisce nel LiveData
    fun readCategorie(){
        lista_cat.clear()
        for(corso in lista_corsi){
            lista_cat.add(corso.categoria)
        }
        listCategorie.postValue(lista_cat)
    }

    //funzione che estrae le lezioni dai corsi e li inserisce nel LiveData
    fun readLezioni(){
        lista_lezioni.clear()
        for(corso in lista_corsi){
            val tmp_list = ArrayList<Lezione>()
            for(lezione in corso.lezioni) {
                tmp_list.add(lezione)
            }
            lista_lezioni.put(corso.id, tmp_list)
        }
        listLezioni.postValue(lista_lezioni)
    }

    //funzione che estrae le lezioni dai corsi e li inserisce nel LiveData
    fun readDocumenti(){
        lista_dispense.clear()
        for(corso in lista_corsi){
            val tmp_list = ArrayList<Documento>()
            for(documento in corso.dispense){
                tmp_list.add(documento)
            }
            lista_dispense.put(corso.id,tmp_list)
        }
        listDispense.postValue(lista_dispense)
    }
    fun getListaCorsi(): MutableLiveData<List<Corso>> {
        return listCorsi
    }
    fun getListaLezioni(): MutableLiveData<HashMap<String, ArrayList<Lezione>>> {
        System.out.println("EntraLezione")
        return listLezioni
    }

    fun getCategorie(): MutableLiveData<Set<String>> {
        System.out.println("EntraCategoria")
        return listCategorie
    }

    fun getListaDispense(): MutableLiveData<HashMap<String, ArrayList<Documento>>> {
        System.out.println("EntraDispense")
        return listDispense
    }
    fun getAggiuntiDiRecente(): MutableLiveData<List<Corso>> {
        return listAggiuntiDiRecente
    }

}
