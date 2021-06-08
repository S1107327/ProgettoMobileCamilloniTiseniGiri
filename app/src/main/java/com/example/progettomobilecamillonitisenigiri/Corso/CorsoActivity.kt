package com.example.progettomobilecamillonitisenigiri.Corso

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityCorsoBinding


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
    }
}