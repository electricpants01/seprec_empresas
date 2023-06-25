package com.locotodevteam.seprecempresas.view.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val companyService: CompanyService
) : ViewModel() {

    var searchCompany = MutableLiveData<String>()
    var companyResult = MutableLiveData<ConnectionResult>()

    init {
        searchCompany("ypfb")
    }

    fun searchCompany(company: String) {
        searchCompany.value = company
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val response = companyService.getCompany(company)
                    if (response?.isSuccessful == true) {
                        val body = response?.body()
                        body.let { body ->
                            withContext(Dispatchers.Main) {
                                companyResult.value = ConnectionResult.NetworkSuccess(body)
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main){
                            companyResult.value =
                                ConnectionResult.NetworkFailure("No se encontró la empresa")
                        }
                }
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main){
                    companyResult.value =
                        ConnectionResult.NetworkFailure("No hay conexión a internet")
                }
            }
        }
    }
}