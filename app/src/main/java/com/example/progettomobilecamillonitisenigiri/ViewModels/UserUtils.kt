package com.example.progettomobilecamillonitisenigiri.ViewModels

import android.util.Log
import com.example.progettomobilecamillonitisenigiri.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

class UserUtils {
    private var loggedUser = FirebaseAuth.getInstance().currentUser
    private lateinit var utenteLoggato: User

    init {

    }

    fun readData(snapshot: DataSnapshot) {

        if (snapshot.child("Users").child(loggedUser!!.uid)!!.exists()) {
            val utenteSnap = snapshot.child("Users").child(loggedUser!!.uid)
            utenteLoggato = utenteSnap.getValue(User::class.java)!!

        }

    }

    fun getUtente(): User {
        return utenteLoggato
    }

    fun getCategoriePreferite(): List<String> {
        return utenteLoggato.categoriePref
    }

    fun getIscrizioni(): List<String> {
        return utenteLoggato.iscrizioni
    }

    //Aggiunge/Elimina iscrizione aggiornando intera lista iscrizioni
    fun setIscrizione(iscrizione_id: String) {
        //val tmp = hashSetOf<String>()
        if(utenteLoggato.iscrizioni.contains(iscrizione_id))
            utenteLoggato.iscrizioni.remove(iscrizione_id)
        else
            utenteLoggato.iscrizioni.add(iscrizione_id)
        /*tmp.addAll(utenteLoggato.iscrizioni)
        utenteLoggato.iscrizioni.clear()
        utenteLoggato.iscrizioni.addAll(tmp)*/
    }
}