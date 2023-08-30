package com.locotodevteam.seprecempresas.view.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.isNetworkConnected
import com.locotodevteam.seprecempresas.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val context: Context,
    private val searchNetworkRepository: SearchNetworkRepository,
    private val searchDatabaseRepository: SearchDatabaseRepository
) : ViewModel() {

    private var searchCompany = MutableLiveData<String>()
    var companyResult = MutableLiveData<NetworkResult>()

    init {
        searchCompany("ypfb")
    }

    fun searchCompany(company: String) {
        searchCompany.value = company
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (context.isNetworkConnected())
                searchNetworkRepository.searchCompany(company) else searchDatabaseRepository.searchCompany(
                company
            )
            withContext(Dispatchers.Main) {
                companyResult.value = result
            }
        }
    }
}