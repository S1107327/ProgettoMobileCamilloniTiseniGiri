package com.example.progettomobilecamillonitisenigiri

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class FragmentUser : Fragment() {
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private var tvFirstName: EditText? = null
    private var tvLastName: EditText? = null
    private var tvEmail: TextView? = null
    lateinit var mSaveBtn: Button
    lateinit var chipGroup1 : ChipGroup
    lateinit var chipGroup2 : ChipGroup
    val model:FirebaseConnection by viewModels()

    private var mUser:FirebaseUser?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        chipGroup1 = view.findViewById<ChipGroup>(R.id.chipGroupUser1)
        chipGroup2 = view.findViewById<ChipGroup>(R.id.chipGroupUser2)
        model.getCategorie().observe(viewLifecycleOwner, Observer<Set<String>>{ categorie->
            for (i in 0..categorie.size-1) {
                var chip = inflater.inflate(R.layout.chip_catalogo, chipGroup1, false) as Chip
                var chip2 = inflater.inflate(R.layout.chip_catalogo, chipGroup2, false) as Chip
                if (i % 2 == 0) {
                    chip.id = i
                    chip.text = categorie.elementAt(i)
                    chip.isCheckable = true
                    chipGroup1.setOnCheckedChangeListener { group, checkedId ->
                        view.findViewById<Chip>(checkedId).isChecked =  !view.findViewById<Chip>(checkedId).isChecked
                    }
                    chipGroup1.addView(chip)
                } else {
                    chip2.id = i
                    chip2.text = categorie.elementAt(i)
                    chip2.isCheckable = true
                    chipGroup2.setOnCheckedChangeListener { group, checkedId ->
                        view.findViewById<Chip>(checkedId).isChecked =  !view.findViewById<Chip>(checkedId).isChecked
                    }
                    chipGroup2.addView(chip2)
                }
            }
        })

        return view
    }
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
       /* chipGroup1

        Log.d("MSG",chipGroup1.checkedChipIds.toString())
        for (a in chipGroup1.checkedChipIds){
            val chip = chipGroup1.findViewById<Chip>(id)
            Log.d("Valore Chip", chip.text.toString())
        }*/


    }

    private fun initialise() {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvFirstName = view?.findViewById<View>(R.id.tv_first_name) as EditText
        tvLastName = view?.findViewById<View>(R.id.tv_last_name) as EditText
        tvEmail = view?.findViewById<View>(R.id.tv_email) as TextView
        mSaveBtn = view?.findViewById(R.id.save_btn) as Button

        mSaveBtn.setOnClickListener{
            model.setUtente(tvFirstName!!.text.toString(),tvLastName!!.text.toString())

            Toast.makeText(context,"Modifiche salvate correttamente", Toast.LENGTH_LONG).show()
        }
    }


}