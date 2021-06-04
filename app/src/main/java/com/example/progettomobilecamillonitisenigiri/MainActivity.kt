package com.example.progettomobilecamillonitisenigiri

import android.app.SearchManager
import android.content.Intent

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.Corso.CorsoActivity
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), PopularAdapter.OnPopularAdapterListener {

    lateinit var mAuth: FirebaseAuth
    lateinit var mLogoutBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
        val corso1:Corsi = Corsi()
        val corso2:Corsi = Corsi()
        val corso3:Corsi = Corsi()
        val corso4:Corsi = Corsi()
        val corso5:Corsi = Corsi()
        if (navController.currentDestination?.id == R.id.FragmentHome) {
            val rvPopolari: RecyclerView? = this.findViewById(R.id.recyclerViewPopolari)
            rvPopolari?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvPopolari?.adapter =
                PopularAdapter(mutableListOf<Corsi>(corso1, corso2, corso3, corso4, corso5))
            val rvConsigliati: RecyclerView? = this.findViewById(R.id.recyclerViewConsigliati)
            rvConsigliati?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvConsigliati?.adapter =
                PopularAdapter(mutableListOf<Corsi>(corso1, corso2, corso3, corso4, corso5))
            val rvRecenti: RecyclerView? = this.findViewById(R.id.recyclerViewRecenti)
            rvRecenti?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvRecenti?.adapter =
                PopularAdapter(mutableListOf<Corsi>(corso1, corso2, corso3, corso4, corso5))
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
            Toast.makeText(applicationContext, "Login Successfully ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNoteClick(position: Int) {
        intent = Intent(this,CorsoActivity::class.java)
        startActivity(intent)
    }
}