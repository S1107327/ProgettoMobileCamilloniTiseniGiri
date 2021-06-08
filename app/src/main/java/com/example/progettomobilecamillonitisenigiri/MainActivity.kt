package com.example.progettomobilecamillonitisenigiri

import android.app.AlertDialog
import android.app.SearchManager
import android.content.DialogInterface
import android.content.Intent

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.User
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection

import com.example.progettomobilecamillonitisenigiri.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mLogoutBtn: Button
    lateinit var firebaseConnection:FirebaseConnection
    var isThefirstTime = true
    val model: FirebaseConnection by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isThefirstTime = savedInstanceState.getBoolean("firstTime")
        }
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setupWithNavController(navController)
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.faq -> {
                    val intent = Intent(this,CorsoActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.share -> {
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY, "share")
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                    true
                }
                R.id.logout -> {
                    mAuth = FirebaseAuth.getInstance()
                    FirebaseAuth.getInstance().signOut()
                    val startIntent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(startIntent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()

        } else {
            model.getUser().observe(this, Observer<User> { utente->
                if(utente.categoriePref.isEmpty()){
                    if(isThefirstTime) {
                        isThefirstTime = false
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Indica le tue Categorie Preferite")
                        alertDialog.setMessage("Indicare le tue categorie preferite servirà all'app per aumentare la tua user experience")
                        alertDialog.setPositiveButton(
                            "OK",
                            DialogInterface.OnClickListener() { dialog, which ->
                                this.findNavController(R.id.myNavHostFragment)
                                    .navigate(R.id.action_FragmentHome_to_FragmentUser)
                            })
                        alertDialog.setNegativeButton("Più Tardi", null)
                        alertDialog.show()
                    }
                }
            })


            Toast.makeText(applicationContext, "Login Successfully ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("firstTime",isThefirstTime)
        super.onSaveInstanceState(outState)
    }
}