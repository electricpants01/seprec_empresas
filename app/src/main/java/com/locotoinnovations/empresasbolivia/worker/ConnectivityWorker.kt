package com.locotoinnovations.seprecbolivia.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.locotoinnovations.seprecbolivia.isNetworkConnected

class ConnectivityWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // check if there is internet connection
            val isConnected = applicationContext.isNetworkConnected()
            if (isConnected) {
                // return success
                Result.success()
            } else {
                // return failure
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("MyWorker", "Error executing work: ${e.message}")
            // return failure
            Result.failure()
        }
    }
}