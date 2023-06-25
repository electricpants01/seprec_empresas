package com.locotodevteam.seprecempresas.broadcaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.locotodevteam.seprecempresas.isNetworkConnected

class ConnectivityBroadcast(private val onConnectivityChange: ConnectivityCallback) :
    BroadcastReceiver() {

    // this is done because of gradlew lint
    interface ConnectivityCallback {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    constructor() : this(object : ConnectivityCallback {
        override fun onNetworkConnectionChanged(isConnected: Boolean) {}
    })

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = context?.isNetworkConnected()
        if (isConnected == true) {
            onConnectivityChange.onNetworkConnectionChanged(true)
        } else {
            onConnectivityChange.onNetworkConnectionChanged(false)
        }
    }
}