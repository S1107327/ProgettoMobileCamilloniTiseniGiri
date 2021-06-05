package com.example.progettomobilecamillonitisenigiri.Model


class Corso(
    titolo: String,
    descrizione: String,
    id: String,
    immagine: String,
    categoria: String,
    lezioni: ArrayList<Lezione>?,
    dispense: ArrayList<Documento>?
) {
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
    constructor() : this("", "", "", "", "",null,null) {}

}