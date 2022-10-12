package com.locotodevteam.seprecempresas.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavController) as NavHostFragment
        val navController = navHostFragment.navController
        mainBinding.myBottomNavView.setupWithNavController(navController)
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        return super.navigateUpTo(upIntent) || findNavController(R.id.myNavController).navigateUp()
    }


}