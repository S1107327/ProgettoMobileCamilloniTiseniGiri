package com.example.progettomobilecamillonitisenigiri.ViewModels

import com.example.progettomobilecamillonitisenigiri.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

class UserUtils {
    private var loggedUser = FirebaseAuth.getInstance().currentUser
    private lateinit var utenteLoggato: User

    init {

    }

    fun readData(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
            utenteLoggato = snapshot.getValue(User::class.java)!!
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
        if(utenteLoggato.iscrizioni.contains(iscrizione_id))
            utenteLoggato.iscrizioni.remove(iscrizione_id)
        else
            utenteLoggato.iscrizioni.add(iscrizione_id)
    }
    fun getWishlist(): List<String> {
        return utenteLoggato.wishlist
    }
    //Aggiunge/Elimina iscrizione aggiornando intera lista iscrizioni
    fun setWishlist(iscrizione_id: String) {
        if(utenteLoggato.wishlist.contains(iscrizione_id))
            utenteLoggato.wishlist.remove(iscrizione_id)
        else
            utenteLoggato.wishlist.add(iscrizione_id)
    }

}