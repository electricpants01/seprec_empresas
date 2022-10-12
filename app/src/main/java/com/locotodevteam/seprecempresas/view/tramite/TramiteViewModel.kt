package com.locotodevteam.seprecempresas.view.tramite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locotodevteam.seprecempresas.model.TramiteDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TramiteViewModel: ViewModel() {

    val tramiteDetail = MutableLiveData<TramiteDetail>()
    val tramiteFailed = MutableLiveData<Boolean>()

    fun getTramiteDetail(tramiteId: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getRetrofitInstance().create(CompanyService::class.java).getTramiteDetail(tramiteId)
            if(response.isSuccessful){
                val body = response.body()
                body?.let { tramiteDetail.postValue(it) }
            }else{
                tramiteFailed.postValue(true)
            }
        }
    }
}