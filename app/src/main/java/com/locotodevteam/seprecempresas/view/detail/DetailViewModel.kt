package com.locotodevteam.seprecempresas.view.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.model.CompanyDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailNetworkRepository: DetailNetworkRepository
) : ViewModel() {

    val companyDetailResult = MutableLiveData<ConnectionResult>()

    fun getCompanyDetail(companyId: String) {
        viewModelScope.launch {
            val result = detailNetworkRepository.getCompanyDetail(companyId)
            withContext(Dispatchers.Main) {
                companyDetailResult.value = result
            }
        }
    }

}