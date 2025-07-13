package com.locototeam.seprecbolivia.ui.view.screen.tramite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locototeam.core.arch.SeprecScaffold
import com.locototeam.core.arch.seprecScaffold
import com.locototeam.core.model.TramiteDetail
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TramiteViewModel @Inject constructor(
    private val tramiteRepository: CompanyRepository
) : ViewModel(), SeprecScaffold<TramiteDetailUiState, TramiteDetailEvent, TramiteDetailSideEffect> by seprecScaffold(
    initialState = TramiteDetailUiState(tramiteDetail =  TramiteDetail.empty())
){

    override fun handleEvent(event: TramiteDetailEvent) {
        when(event) {
            is TramiteDetailEvent.OnSearchTextChange -> onSearchTextChange(event.searchText)
        }
    }
    fun getTramiteDetail(tramiteId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = tramiteRepository.getTramiteDetail(tramiteId = tramiteId)) {
                is NetworkResult.NetworkSuccess<*> -> {
                    val result = response.data as TramiteDetail
                    updateViewState { it.copy(tramiteDetail = result) }
                }

                is NetworkResult.NetworkEmptyResult<*> -> {
                    viewModelScope.emitSideEffect(TramiteDetailSideEffect.TramiteEmptyData)
                }

                is NetworkResult.NetworkFailure -> {
                    viewModelScope.emitSideEffect(TramiteDetailSideEffect.FetchTramiteDetailFail)
                }
            }
        }
    }

    fun onSearchTextChange(searchText: String) {
        updateViewState { it.copy(searchText = searchText) }
        getTramiteDetail(searchText)
    }

    sealed class TramiteEvent {
        object OnTramiteEmptyData : TramiteEvent()
        object OnFetchTramiteDetailFail : TramiteEvent()
    }
}

internal data class TramiteDetailUiState(
    val tramiteDetail: TramiteDetail,
    val searchText: String = "",
)

interface TramiteDetailEvent {
    data class OnSearchTextChange(val searchText: String) : TramiteDetailEvent
}

internal interface TramiteDetailSideEffect {
    data object TramiteEmptyData : TramiteDetailSideEffect
    data object FetchTramiteDetailFail : TramiteDetailSideEffect

}