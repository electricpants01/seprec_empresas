package com.locotodevteam.seprecempresas.view.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.isNetworkConnected
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val context: Context,
    private val searchNetworkRepository: SearchNetworkRepository,
    private val searchDatabaseRepository: SearchDatabaseRepository
) : ViewModel() {

    var searchCompany = MutableLiveData<String>()
    var companyResult = MutableLiveData<ConnectionResult>()

    init {
        searchCompany("ypfb")
    }

    fun searchCompany(company: String) {
        searchCompany.value = company
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (context.isNetworkConnected())
                searchNetworkRepository.searchCompany(company) else searchDatabaseRepository.searchCompany(company)
            withContext(Dispatchers.Main) {
                companyResult.value = result
            }
        }
    }
}