package com.example.progettomobilecamillonitisenigiri.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserViewModel: ViewModel() {

    //Riferimenti al Database
    private val mDatabase = FirebaseDatabase.getInstance()          //riferimento a FireBase
    private val mDatabaseReference = mDatabase.reference            //riferimento al Database
    private val loggedUser = FirebaseAuth.getInstance().currentUser //riferimento all'utente loggato
    private val categoriePreferiteAppoggio = ArrayList<String>()
    //LiveData
    private val categoriePreferite = MutableLiveData<List<String>>()
    private val currentUser = MutableLiveData<User>()
    init{
        mDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                readUtente(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    //legge i dati dell'utente attualmente loggato e li inserisce dentro le variabili currentUser e categoriePreferite
    fun readUtente(snapshot: DataSnapshot) {
        if (snapshot.child("Users").child(loggedUser!!.uid).exists()) {
            val utenteSnap = snapshot.child("Users").child(loggedUser!!.uid)
            val utente = utenteSnap.getValue(User::class.java) as User
            currentUser.postValue(utente)
            categoriePreferite.postValue(utente.categoriePref)
            categoriePreferiteAppoggio.addAll(utente.categoriePref)
        }
    }

    //setta i dati dell'utente attualmente loggato usando l'oggeto utente passato come parametro
    fun setUtente(utente: User) {
        val utenti = mDatabaseReference.child("Users")
        loggedUser.let { it1 ->
            utenti.child(it1!!.uid).setValue(utente)
        }
    }
    fun getUser(): MutableLiveData<User> {
        return currentUser
    }

    //Funzione che ritorna lista consigliati iterando una Lista di Corsi
    fun getListaConsigliati(corsi: ArrayList<Corso>): ArrayList<Corso> {
        val consigliati = ArrayList<Corso>()
        for (corso in corsi) {
            for (categoria in categoriePreferiteAppoggio) {
                if (corso.categoria.equals(categoria)) {
                    consigliati.add(corso)
                }
            }

        }
        if(consigliati.isEmpty())
            return corsi //ritorna corsi se l'utente non ha categorie predefinite
        return consigliati
    }
}