package com.example.progettomobilecamillonitisenigiri.Corso

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.progettomobilecamillonitisenigiri.Main.MainActivity
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityCorsoBinding
import com.google.android.material.appbar.MaterialToolbar


class CorsoActivity : AppCompatActivity() {
    val model: FirebaseConnection by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCorsoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_corso)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostCorsoFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomCorsoNavigation.setupWithNavController(navController)
        setSupportActionBar(binding.topAppBarCorso)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        model.getListaCorsi().observe(this, Observer<List<Corso>> { corsi->
            for(corso in corsi){
                if(corso.id.equals(intent.getStringExtra("ID_CORSO").toString())) {
                    findViewById<MaterialToolbar>(R.id.topAppBarCorso).title = corso.titolo
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //val intent = Intent(this, MainActivity::class.java)
                //startActivity(intent)
                finish()
            }
        }
        return true
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}