package com.locotodevteam.seprecempresas.view.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locotodevteam.seprecempresas.model.CompanyDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    val companyDetail = MutableLiveData<CompanyDetail>()
    val companyLoadError = MutableLiveData<Boolean>()

    fun getCompanyDetail(companyId: String, context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getRetrofitInstance(context).create(CompanyService::class.java).getCompanyDetail(companyId)
            if (response.isSuccessful){
                val body = response.body()
                body?.let { details ->
                    companyDetail.postValue(details)
                }
            } else {
                Log.d("chris", "getCompanyDetail error: ${response.errorBody()}")
                companyLoadError.postValue(true)
            }
        }
    }

}