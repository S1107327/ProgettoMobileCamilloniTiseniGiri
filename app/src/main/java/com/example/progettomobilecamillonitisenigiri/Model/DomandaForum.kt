package com.example.progettomobilecamillonitisenigiri.Model

class DomandaForum(val nomeUtente:String,val cognomeUtente:String,val id:Int, var Domanda:String ,val Risposte:ArrayList<RispostaForum>) {
    constructor() : this("Utente" ,"Utente",0, "Assente", ArrayList<RispostaForum>())

    override fun equals(other: Any?): Boolean {
        return if(other is DomandaForum) {
            (other.nomeUtente == nomeUtente && other.cognomeUtente == cognomeUtente && other.Domanda == Domanda)
        } else false
    }
}