package com.example.progettomobilecamillonitisenigiri.Model

class UltimaLezione(
     id_corso: String ,
     id_lezione: String ,
     secondi: Int
){
    var secondi: Int
    var id_lezione: String
    var id_corso: String

    init {
    this.id_corso=id_corso
    this.id_lezione=id_lezione
    this.secondi=secondi

}
constructor() : this( "","",0) {}
}