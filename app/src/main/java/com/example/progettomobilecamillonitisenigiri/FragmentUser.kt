package com.example.progettomobilecamillonitisenigiri

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class FragmentUser : Fragment(R.layout.fragment_user) {
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private var tvFirstName: EditText? = null
    private var tvLastName: EditText? = null
    private var tvEmail: TextView? = null
    lateinit var mSaveBtn: Button

    private var mUser:FirebaseUser?=null

    override fun onStart() {
        super.onStart()

        mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        tvEmail!!.text = mUser!!.email


        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFirstName!!.setText(snapshot.child("firstName").value as String)
                tvLastName!!.setText(snapshot.child("lastName").value as String)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()


    }

    private fun initialise() {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        tvFirstName = getView()?.findViewById<View>(R.id.tv_first_name) as EditText
        tvLastName = getView()?.findViewById<View>(R.id.tv_last_name) as EditText
        tvEmail = getView()?.findViewById<View>(R.id.tv_email) as TextView
        mSaveBtn = getView()?.findViewById(R.id.save_btn) as Button

        mSaveBtn.setOnClickListener{
            mUser?.let { it1 -> mDatabaseReference!!.child(it1.uid).child("firstName").setValue(
                tvFirstName!!.text.toString()) }
            mUser?.let { it1 -> mDatabaseReference!!.child(it1.uid).child("lastName").setValue(tvLastName!!.text.toString()) }
        }
    }


}