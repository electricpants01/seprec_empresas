package com.locotodevteam.seprecempresas.view.tramite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.locotodevteam.seprecempresas.model.TramiteDetail
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TramiteViewModel @Inject constructor(
    private val companyService: CompanyService
) : ViewModel() {

    val tramiteDetailResult = MutableLiveData<ConnectionResult>()

    fun getTramiteDetail(tramiteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = companyService.getTramiteDetail(tramiteId)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {body ->
                        withContext(Dispatchers.Main) {
                            tramiteDetailResult.postValue(ConnectionResult.NetworkSuccess(body))
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        tramiteDetailResult.postValue(ConnectionResult.NetworkFailure("Error al obtener el trámite"))
                    }
                }
            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) {
                    tramiteDetailResult.postValue(ConnectionResult.NetworkFailure("Error de conexión"))
                }
            }
        }
    }
}