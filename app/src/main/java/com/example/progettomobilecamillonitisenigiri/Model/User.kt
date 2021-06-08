package com.example.progettomobilecamillonitisenigiri.Model

class User (uid: String, firstName: String, lastName: String, corsi: ArrayList<String>, categorie: ArrayList<String>) {
    var uid: String
    var firstName: String
    var lastName: String
    var corsi: ArrayList<String> //id corsi
    var categorie: ArrayList<String> //categorie
    init {
        this.uid=uid
        this.firstName=firstName
        this.lastName=lastName
        this.corsi=corsi
        this.categorie=categorie
        if(corsi == null)
            this.corsi = ArrayList<String>()
        if(categorie == null)
            this.categorie = ArrayList<String>()

    }
    constructor() : this("", "", "",ArrayList<String>(),ArrayList<String>()) {}
}