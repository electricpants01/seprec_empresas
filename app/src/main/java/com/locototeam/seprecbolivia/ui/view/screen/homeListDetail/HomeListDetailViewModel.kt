package com.locototeam.seprecbolivia.ui.view.screen.homeListDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locototeam.core.arch.SeprecScaffold
import com.locototeam.core.arch.seprecScaffold
import com.locototeam.core.model.CompanyDetail
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.retrofit.NetworkResult
import com.locototeam.seprecbolivia.ui.view.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companyRepository: CompanyRepository,
) : ViewModel(), SeprecScaffold<HomeListDetailUiState, HomeListDetailEvent, HomeListDetailSideEffect> by seprecScaffold(
    initialState = HomeListDetailUiState(CompanyDetail.empty())
) {

    private val companyId: String = savedStateHandle.get<String>(MainActivity.ARG_NAVIGATION_SEARCH_ID)!!

    override fun handleEvent(event: HomeListDetailEvent) {
        when (event) {
            is HomeListDetailEvent.InitContent -> initContent()
        }
    }

    private fun getCompanyDetail(companyId: String) {
        viewModelScope.launch {
            val result = companyRepository.getCompanyDetail(companyId = companyId)
            withContext(Dispatchers.Main) {
                when (result) {
                    is NetworkResult.NetworkSuccess<*> -> {
                        val result = result.data as CompanyDetail
                        updateViewState { it.copy(companyDetail = result) }
                    }

                    is NetworkResult.NetworkEmptyResult<*> -> {
                        viewModelScope.emitSideEffect(HomeListDetailSideEffect.CompanyDetailNotFound())
                    }

                    is NetworkResult.NetworkFailure -> {
                        viewModelScope.emitSideEffect(HomeListDetailSideEffect.NetworkError())
                    }
                }

            }
        }
    }

    private fun initContent() {
        getCompanyDetail(companyId)
    }
}

internal data class HomeListDetailUiState(
    val companyDetail: CompanyDetail,
)

interface HomeListDetailEvent {
    data object InitContent : HomeListDetailEvent
}

internal interface HomeListDetailSideEffect {
    data class CompanyDetailNotFound(val message: String = "Company detail not found") : HomeListDetailSideEffect
    data class NetworkError(val message: String = "Network error") : HomeListDetailSideEffect

}