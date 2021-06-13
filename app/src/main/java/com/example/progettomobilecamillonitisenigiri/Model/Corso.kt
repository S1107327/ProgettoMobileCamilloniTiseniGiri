package com.example.progettomobilecamillonitisenigiri.Model

import com.google.gson.Gson


class Corso(
    titolo: String,
    descrizione: String,
    docente: String,
    id: String,
    immagine: String,
    prezzo: String,
    categoria: String,
    recensioni: HashMap<String,Float>,
    lezioni: ArrayList<Lezione>?,
    dispense: ArrayList<Documento>?,
    forum: ArrayList<DomandaForum>
) {
    fun clone(): Corso {
        val corso = Gson().toJson(this)
        return Gson().fromJson(corso,Corso::class.java)

    }

    var titolo: String
    var descrizione: String
    var id: String
    var docente: String
    var immagine: String
    var prezzo: String
    lateinit var recensioni: HashMap<String,Float>
    var categoria: String
    lateinit var lezioni: ArrayList<Lezione>
    lateinit var dispense: ArrayList<Documento>
    lateinit var forum: ArrayList<DomandaForum>


    init {
        this.titolo = titolo
        this.descrizione = descrizione
        this.id = id
        this.docente = docente
        this.immagine = immagine
        this.prezzo = prezzo
        this.categoria = categoria
        if (lezioni != null) {
            this.lezioni = lezioni
        }
        if (dispense != null) {
            this.dispense = dispense
        }
        if (recensioni != null) {
            this.recensioni = recensioni
        }
        if(forum != null){
            this.forum = forum
        }

    }
    constructor() : this("Non specificato", "assente", "Non specificato","0", "assente", "Corso gratuito","assente",
        hashMapOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf()) {

    }

}