package com.example.progettomobilecamillonitisenigiri.Utils

import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseConnection {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    val list = ArrayList<Corso>()
    init {

        database = FirebaseDatabase.getInstance().reference

    }


    interface MyCallback {
        fun onCallback(value: List<Corso>)
    }

    fun readData(myCallback: (List<Corso>) -> Unit) {
        database?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Corsi")!!.exists()) {
                    list.clear()
                    for (e in snapshot.child("Corsi").children) {
                        val corso = e.getValue(Corso::class.java)
                        list.add(corso!!)
                    }
                }
                myCallback(list)
                System.out.println("OKKKK")
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
    fun getListaCorsi(): ArrayList<Corso> {
        System.out.println("Entra")
        return list
    }
}