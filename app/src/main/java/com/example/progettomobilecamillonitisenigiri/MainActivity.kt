package com.example.progettomobilecamillonitisenigiri

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }
}
