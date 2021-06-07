package com.example.progettomobilecamillonitisenigiri.Model

import com.google.gson.Gson
import org.w3c.dom.Document


class Corso(
    titolo: String,
    descrizione: String,
    id: String,
    immagine: String,
    categoria: String,
    lezioni: ArrayList<Lezione>?,
    dispense: ArrayList<Documento>?
) {
    fun clone(): Corso {
        val corso = Gson().toJson(this)
        return Gson().fromJson(corso,Corso::class.java)

    }

    var titolo: String
    var descrizione: String
    var id: String
    var immagine: String
    var categoria: String
    lateinit var lezioni: ArrayList<Lezione>
    lateinit var dispense: ArrayList<Documento>


    init {
        this.titolo = titolo
        this.descrizione = descrizione
        this.id = id
        this.immagine = immagine
        this.categoria = categoria
        if (lezioni != null) {
            this.lezioni = lezioni
        }
        if (dispense != null) {
            this.dispense = dispense
        }
    }
    constructor() : this("Assente", "assente", "0", "assente", "assente",null,null) {

    }

}