package com.locotoinnovations.seprecbolivia.ui.view.screen.homeListDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotoinnovations.core.model.CompanyDetail
import com.locotoinnovations.core.repository.CompanyRepository
import com.locotoinnovations.core.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeListDetailViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
) : ViewModel() {

    private val _companyDetail = MutableStateFlow(CompanyDetail.empty())
    val companyDetail: StateFlow<CompanyDetail> = _companyDetail.asStateFlow()

    private val _event = MutableStateFlow<HomeListDetailEvent?>(null)
    val event: StateFlow<HomeListDetailEvent?> = _event.asStateFlow()

    fun getCompanyDetail(companyId: String) {
        viewModelScope.launch {
            val result = companyRepository.getCompanyDetail(companyId = companyId)
            withContext(Dispatchers.Main) {
                when (result) {
                    is NetworkResult.NetworkSuccess<*> -> {
                        _companyDetail.value = result.data as CompanyDetail
                        _event.emit(HomeListDetailEvent.DetailNotFound("No company detail found"))
                    }

                    is NetworkResult.NetworkEmptyResult<*> -> {
                        _event.emit(HomeListDetailEvent.DetailNotFound("No company detail found"))
                    }

                    is NetworkResult.NetworkFailure -> {
                        _event.emit(HomeListDetailEvent.NetworkError("Network error"))
                    }
                }

            }
        }
    }

    sealed class HomeListDetailEvent {
        data class DetailNotFound(val message: String) : HomeListDetailEvent()
        data class NetworkError(val message: String) : HomeListDetailEvent()
    }
}