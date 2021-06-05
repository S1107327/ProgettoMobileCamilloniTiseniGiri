package com.example.progettomobilecamillonitisenigiri.Model

class Documento(titolo: String, id: String) {
      var titolo: String
      var id: String
    init {
        this.id=id
        this.titolo=titolo
    }
    constructor() : this("", "") {}
}