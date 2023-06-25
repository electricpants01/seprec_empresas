package com.locotodevteam.seprecempresas.view.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.model.CompanyDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val companyService: CompanyService
) : ViewModel() {

    val companyDetail = MutableLiveData<CompanyDetail>()
    val companyLoadError = MutableLiveData<Boolean>()

    fun getCompanyDetail(companyId: String) {
        viewModelScope.launch {
            val response = companyService.getCompanyDetail(companyId)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { details ->
                    companyDetail.postValue(details)
                }
            } else {
                companyLoadError.postValue(true)
            }
        }
    }

}