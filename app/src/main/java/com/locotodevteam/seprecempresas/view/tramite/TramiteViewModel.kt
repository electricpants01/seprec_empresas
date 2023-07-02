package com.locotodevteam.seprecempresas.view.tramite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.model.TramiteDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TramiteViewModel @Inject constructor(
    private val companyService: CompanyService
) : ViewModel() {

    val tramiteDetailResult = MutableLiveData<NetworkResult>()

    fun getTramiteDetail(tramiteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = companyService.getTramiteDetail(tramiteId)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        withContext(Dispatchers.Main) {
                            tramiteDetailResult.postValue(NetworkResult.NetworkSuccess(body))
                        }
                    }
                } else {
                    // obtenemos code() == 412
                    withContext(Dispatchers.Main) {
                        tramiteDetailResult.postValue(NetworkResult.NetworkFailure(R.string.tramite_not_found))
                    }
                }
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) {
                    tramiteDetailResult.postValue(NetworkResult.NetworkFailure(R.string.network_error))
                }
            }
        }
    }
}