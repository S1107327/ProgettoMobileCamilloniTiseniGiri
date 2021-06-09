package com.example.progettomobilecamillonitisenigiri.ViewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Documento
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class FirebaseConnection : ViewModel() {

    private var database: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

    //private var mDatabaseReference: DatabaseReference
    private var userDatabaseReference: DatabaseReference
    private var loggedUser = FirebaseAuth.getInstance().currentUser


    // Mutable Live Data
    private val listCorsi = MutableLiveData<List<Corso>>()
    private val listAggiuntiDiRecente = MutableLiveData<List<Corso>>()
    private val listCategorie = MutableLiveData<Set<String>>()
    private val listLezioni = MutableLiveData<HashMap<String, ArrayList<Lezione>>>()
    private val listDispense = MutableLiveData<HashMap<String, ArrayList<Documento>>>()
    private val listCorsiPerCat = MutableLiveData<HashMap<String, ArrayList<Corso>>>()


    // Live data relativi tabella utente
    private val categoriePreferite = MutableLiveData<List<String>>()
    private val currentUser = MutableLiveData<User>()

    private val corsoUtils: CorsoUtils = CorsoUtils()
    private val utenteUtils: UserUtils = UserUtils()

    init {
        //mDatabaseReference = FirebaseDatabase.getInstance().reference
        database = FirebaseDatabase.getInstance().reference
        userDatabaseReference = database!!.child("Users")
        readData()
    }

    fun readData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Chiamata a utility utente e popolazione livedata
                utenteUtils.readData(snapshot)
                currentUser.postValue(utenteUtils.getUtente())
                categoriePreferite.postValue(utenteUtils.getCategoriePreferite())

                //Chiamata a utility per popolazione liste corsi, e relative sottocategorie
                corsoUtils.readData(snapshot)
                listCorsi.postValue(corsoUtils.getCorsi())
                listLezioni.postValue(corsoUtils.getLezioni())
                listAggiuntiDiRecente.postValue(
                    corsoUtils.getCorsi().reversed()
                )//oppure takeLast(numero)
                listCategorie.postValue(corsoUtils.getCat())
                listDispense.postValue(corsoUtils.getDispense())
                listCorsiPerCat.postValue(corsoUtils.getCorsiPerCat())


            }


            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    //questa funzione aggiunge/elimina il corso dalle iscrizioni richiamando la setiscrizione del corsoUtils
    fun iscriviti(id_corso: String) {
        utenteUtils.setIscrizione(id_corso)
        loggedUser.let { it1 ->
            userDatabaseReference!!.child(it1!!.uid).setValue(currentUser.value as User)
        }
    }

    fun wishlist(id_corso: String) {
        utenteUtils.setWishlist(id_corso)
        loggedUser.let { it1 ->
            userDatabaseReference!!.child(it1!!.uid).setValue(currentUser.value as User)
        }
    }

    fun setUtente(utente: User) {
        loggedUser.let { it1 ->
            userDatabaseReference!!.child(it1!!.uid).setValue(utente)
        }
    }


    fun getUser(): MutableLiveData<User> {
        return currentUser
    }

    fun getListaCorsi(): MutableLiveData<List<Corso>> {
        return listCorsi
    }

    /* *********************** Bisogna fare refactor del codice qui******************* */

    //Funzione che ritorna lista consigliati iterando una Lista di Corsi
//Non utilizza liveData
    fun getListaConsigliati(corsi: ArrayList<Corso>): ArrayList<Corso> {
        val consigliati = ArrayList<Corso>()
        for (corso in corsi) {
            for (categoria in currentUser.value!!.categoriePref) {
                if (corso.categoria.equals(categoria)) {
                    consigliati.add(corso)
                }
            }

        }
        if (consigliati.isEmpty())
            return corsi //ritorna corsi se l'utente non ha categorie predefinite
        return consigliati
    }

    fun getCorsiFrequentati(corsi: ArrayList<Corso>): ArrayList<Corso> {
        val frequentati = ArrayList<Corso>()
        for (corso in corsi) {
            for (iscrizioni_id in utenteUtils.getIscrizioni()) {
                if (corso.id.equals(iscrizioni_id)) {
                    frequentati.add(corso)
                }
            }

        }
        return frequentati
    }

    fun getCorsiFromWishlist(corsi: ArrayList<Corso>): ArrayList<Corso> {
        val wishlist = ArrayList<Corso>()
        for (corso in corsi) {
            for (iscrizioni_id in utenteUtils.getWishlist()) {
                if (corso.id.equals(iscrizioni_id)) {
                    wishlist.add(corso)
                }
            }

        }
        return wishlist
    }
    fun isIscritto(idCorso:String): Boolean{
        if (currentUser.value?.iscrizioni?.contains(idCorso) == true)
            return true
        return false
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

    fun getCorsiPerCat(): MutableLiveData<HashMap<String, ArrayList<Corso>>> {
        return listCorsiPerCat
    }


/*
    //private var controllerCorsi:CorsiViewModel = CorsiViewModel()
    //private var controllerUser:UserViewModel = UserViewModel()
    //private val listaConsigliati = MutableLiveData<List<Corso>>()

    // private val currentCourse = MutableLiveData<Corso>()
   fun getAggiuntiDiRecente(): MutableLiveData<List<Corso>> {
        return listAggiuntiDiRecente
    }
    fun readUtente(snapshot: DataSnapshot) {

        if (snapshot.child("Users").child(loggedUser!!.uid)!!.exists()) {
            val utenteSnap = snapshot.child("Users").child(loggedUser!!.uid)
            val utente = utenteSnap.getValue(User::class.java)
            currentUser.postValue(utente)
            categoriePreferite.postValue(utente!!.categoriePref)
        }

    }

    fun setUtente(firstName: String, lastName: String) {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")

        loggedUser.let { it1 ->
            mDatabaseReference!!.child(it1!!.uid).child("firstName")
                .setValue(firstName)
        }
        loggedUser.let { it1 ->
            mDatabaseReference!!.child(it1!!.uid).child("lastName")
                .setValue(lastName)
        }


    }*/

}

