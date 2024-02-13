package com.locotoinnovations.seprecbolivia.ui.view.screen.Tramite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotoinnovations.core.model.TramiteDetail
import com.locotoinnovations.core.repository.CompanyRepository
import com.locotoinnovations.core.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TramiteViewModel @Inject constructor(
    private val tramiteRepository: CompanyRepository
): ViewModel() {

    private val _tramiteDetail = MutableStateFlow(TramiteDetail.empty())
    val tramiteDetail = _tramiteDetail.asStateFlow()
    // user searchText
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    // events for tramite
    private val _tramiteEvent = MutableStateFlow<TramiteEvent?>(null)
    val tramiteEvent = _tramiteEvent.asStateFlow()

    fun getTramiteDetail(tramiteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = tramiteRepository.getTramiteDetail(tramiteId = tramiteId)) {
                is NetworkResult.NetworkSuccess<*> -> {
                    val tramiteDetail = response.data as TramiteDetail
                    _tramiteDetail.value = tramiteDetail
                }
                is NetworkResult.NetworkEmptyResult<*> -> {
                    _tramiteEvent.value = TramiteEvent.OnTramiteEmptyData
                }
                is NetworkResult.NetworkFailure -> {
                    _tramiteEvent.value = TramiteEvent.OnFetchTramiteDetailFail
                }
            }
        }
    }

    fun onSearchTextChange(searchText: String) {
        _searchText.value = searchText
        getTramiteDetail(searchText)
    }

    sealed class TramiteEvent {
        object OnTramiteEmptyData: TramiteEvent()
        object OnFetchTramiteDetailFail: TramiteEvent()
    }
}