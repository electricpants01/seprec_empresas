package com.locotodevteam.seprecempresas.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.locotodevteam.seprecempresas.broadcaster.ConnectivityBroadcast
import com.locotodevteam.seprecempresas.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var connectivityReceiver: ConnectivityBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initBottomNavigationView()
        initBroadCastReceiver()
    }

    private fun initBroadCastReceiver() {
        connectivityReceiver = ConnectivityBroadcast()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, filter)
    }

    private fun initBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(mainBinding.myNavController.id) as NavHostFragment
        val navController = navHostFragment.navController
        mainBinding.myBottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(mainBinding.myNavController.id).navigateUp()
    }

    override fun onStop() {
        super.onStop()
        // before this screen is destroyed,
        // we need to unregister the broadcast receiver because it is a memory leak
        unregisterReceiver(connectivityReceiver)
    }

}