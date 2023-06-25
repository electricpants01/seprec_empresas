package com.locotodevteam.seprecempresas.broadcaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.locotodevteam.seprecempresas.isNetworkConnected

class ConnectivityBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = context?.isNetworkConnected()
        if (isConnected == true) {
            Log.d("chris", "estas conectado")
        } else {
            Log.d("chris", "estas desconectado")
        }
    }
}