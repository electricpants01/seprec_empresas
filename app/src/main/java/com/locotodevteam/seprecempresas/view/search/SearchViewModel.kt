package com.locotodevteam.seprecempresas.view.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    var searchCompany = MutableLiveData<String>()
    var companyList = MutableLiveData<Company?>()
    private var firstCall = true

    fun firstCall(){
        // on first call we will search for YPFB
        if(firstCall){
            firstCall = false
            searchCompany("ypfb")
        }
    }

    fun searchCompany(company: String){
        searchCompany.value = company
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getRetrofitInstance().create(CompanyService::class.java).getCompany(company)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null){
                    companyList.postValue(body)
                }
            }
        }
    }
}