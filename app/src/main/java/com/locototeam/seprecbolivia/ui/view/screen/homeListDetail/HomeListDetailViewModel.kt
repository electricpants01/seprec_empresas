package com.locototeam.seprecbolivia.ui.view.screen.homeListDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locototeam.core.model.CompanyDetail
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.retrofit.NetworkResult
import com.locototeam.seprecbolivia.ui.view.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val companyRepository: CompanyRepository,
) : ViewModel() {

    private val companyId: String = savedStateHandle.get<String>(MainActivity.ARG_NAVIGATION_SEARCH_ID)!!

    private val _companyDetail = MutableStateFlow(CompanyDetail.empty())
    val companyDetail: StateFlow<CompanyDetail> = _companyDetail.asStateFlow()

    private val _event = MutableStateFlow<HomeListDetailEvent?>(null)
    val event: StateFlow<HomeListDetailEvent?> = _event.asStateFlow()

    init {
        getCompanyDetail(companyId)
    }

    private fun getCompanyDetail(companyId: String) {
        viewModelScope.launch {
            val result = companyRepository.getCompanyDetail(companyId = companyId)
            when (result) {
                is NetworkResult.NetworkSuccess<*> -> {
                    _companyDetail.value = result.data as CompanyDetail
                }

                is NetworkResult.NetworkEmptyResult<*> -> {
                    _event.tryEmit(HomeListDetailEvent.DetailNotFound("No company detail found"))
                }

                is NetworkResult.NetworkFailure -> {
                    _event.tryEmit(HomeListDetailEvent.NetworkError("Network error"))
                }
            }
        }
    }

    sealed class HomeListDetailEvent {
        data class DetailNotFound(val message: String) : HomeListDetailEvent()
        data class NetworkError(val message: String) : HomeListDetailEvent()
    }
}