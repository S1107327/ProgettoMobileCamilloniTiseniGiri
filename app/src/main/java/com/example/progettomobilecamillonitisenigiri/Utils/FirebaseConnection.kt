package com.example.progettomobilecamillonitisenigiri.Utils

import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseConnection {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    val listCorsi = ArrayList<Corso>()
    val listCategorie = mutableListOf<String>()
    val listLezioni = ArrayList<Lezione>()
    init {

        database = FirebaseDatabase.getInstance().reference

    }


    interface MyCallback {
        fun onCallback(value: List<Corso>)
    }
    interface CallbackCategorie {
        fun onCallback(value: MutableList<String>)
    }
    interface CallbackLezioni {
        fun onCallback(value: ArrayList<Lezione>)
    }

    fun readData(myCallback: (List<Corso>) -> Unit) {
        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Corsi")!!.exists()) {
                    listCorsi.clear()
                    for (e in snapshot.child("Corsi").children) {
                        val corso = e.getValue(Corso::class.java)
                        listCorsi.add(corso!!)
                    }
                }
                myCallback(listCorsi)
                System.out.println("OKKKK")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    //TODO() da finire
    fun readDataLezioni(CallbackLezioni: (List<Lezione>) -> Unit) {
        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Corsi")!!.exists()) {
                    listLezioni.clear()
                    for (e in snapshot.child("Corsi").child("0").child("lezioni").children) {
                        val lezione = e.getValue(Lezione::class.java)
                        listLezioni.add(lezione!!)
                    }
                }
                CallbackLezioni(listLezioni)
                System.out.println("OKKKK")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    fun readCategorie(CallbackCategorie: (MutableList<String>) -> Unit) {
        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Corsi")!!.exists()) {
                    listCategorie.clear()
                    for (e in snapshot.child("Corsi").children) {
                        val corso = e.getValue(Corso::class.java)
                        if(!listCategorie.contains(corso!!.categoria)){
                            listCategorie.add(corso!!.categoria)
                        }
                    }
                }
                CallbackCategorie(listCategorie)
                System.out.println("OKKKK")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
    fun getListaCorsi(): ArrayList<Corso> {
        System.out.println("Entra")
        return listCorsi
    }
    fun getListaLezioni(): ArrayList<Lezione> {
        System.out.println("Entra")
        return listLezioni
    }
    fun getCategorie():MutableList<String>{
        System.out.println("EntraCategoria")
        return listCategorie
    }
}