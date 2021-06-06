package com.example.progettomobilecamillonitisenigiri.Corso

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.MyAdapter
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.Utils.FirebaseConnection
import com.example.progettomobilecamillonitisenigiri.databinding.ActivityCorsoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CorsoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("ID_CORSO")
        Log.d("Prova", id.toString())
        val model: FirebaseConnection by viewModels()
        var corso: Corso? = null
        model.getListaCorsi().observe(this, Observer<List<Corso>> { corsi ->
            for (a in corsi)
                if (a.id.equals(id)) {
                    corso = a
                    Log.d("Ciao", "Corso esistente")
                }

        })

        if (corso == null)
            Toast.makeText(this, "Corso non trovato", Toast.LENGTH_SHORT).show()
        else {

        }


        val binding: ActivityCorsoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_corso)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostCorsoFragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater

        val arg = Bundle()
        arg.putString("ID_CORSO", id)


        val transaction = supportFragmentManager.beginTransaction()

        //supportFragmentManager.findFragmentById(R.id.FragmentInfoCorso) .arguments = arg
        //transaction.replace(R.id.FragmentInfoCorso, FragmentInfoCorso)
        //transaction.addToBackStack(null)
        //transaction.commit()

        val navController = navHostFragment.navController
        navController.setGraph(navHostFragment.navController.graph,arg)
        binding.bottomCorsoNavigation.setupWithNavController(navController)

    }
}