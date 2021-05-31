package com.example.progettomobilecamillonitisenigiri

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    Toast.makeText(
                        this@MainActivity,
                        "You Clicked : " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.faq -> {
                    Toast.makeText(
                        this@MainActivity,
                        "You Clicked : " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.logout ->{
                    Toast.makeText(
                        this@MainActivity,
                        "You Clicked : " + menuItem.title,
                        Toast.LENGTH_SHORT
                    ).show()
                true
                }
                else->false
            }
        }
        val corso1:Corsi = Corsi()
        val corso2:Corsi = Corsi()
        val corso3:Corsi = Corsi()
        val corso4:Corsi = Corsi()
        val corso5:Corsi = Corsi()
        val rvPopolari: RecyclerView = findViewById(R.id.recyclerViewPopolari)
        rvPopolari.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvPopolari.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5))
        val rvConsigliati: RecyclerView = findViewById(R.id.recyclerViewConsigliati)
        rvConsigliati.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvConsigliati.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5))
        val rvRecenti: RecyclerView = findViewById(R.id.recyclerViewRecenti)
        rvRecenti.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvRecenti.adapter = PopularAdapter(mutableListOf<Corsi>(corso1,corso2,corso3,corso4,corso5))
    }
}
