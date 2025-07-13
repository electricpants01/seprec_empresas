package com.locototeam.seprecbolivia.ui.view.screen.homeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locototeam.core.arch.SeprecScaffold
import com.locototeam.core.arch.seprecScaffold
import com.locototeam.core.model.Company
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
) : ViewModel(),
    SeprecScaffold<HomeListUiState, HomeListEvent, HomeListSideEffect> by seprecScaffold(
        initialState = HomeListUiState(Company.empty())
    ) {
    private fun getCompanyList(searchCompanyText: String) {
        viewModelScope.launch {
            val result = companyRepository.getCompany(searchCompanyText)
            when (result) {
                is NetworkResult.NetworkSuccess<*> -> {
                    // emit company list
                    val result = result.data as Company
                    updateViewState {
                        it.copy(company = result)
                    }
                }

                is NetworkResult.NetworkEmptyResult<*> -> {
                    viewModelScope.emitSideEffect(HomeListSideEffect.NoCompanyFound())
                }

                is NetworkResult.NetworkFailure -> {
                    viewModelScope.emitSideEffect(HomeListSideEffect.NoInternetConnection())
                }
            }

        }
    }

    override fun handleEvent(event: HomeListEvent) {
        when (event) {
            is HomeListEvent.InitContent -> initContent()
            is HomeListEvent.SearchCompany -> searchCompany(event.searchCompanyText)
        }
    }

    private fun initContent() {
        getCompanyList("ypfb")
    }

    private fun searchCompany(searchCompanyText: String) {
        updateViewState { it.copy(searchText = searchCompanyText) }
        getCompanyList(searchCompanyText)
    }
}

internal data class HomeListUiState(
    val company: Company,
    val isSearching: Boolean = false,
    val searchText: String = "",
)

interface HomeListEvent {
    data object InitContent : HomeListEvent
    data class SearchCompany(val searchCompanyText: String) : HomeListEvent
}

internal interface HomeListSideEffect {
    data class NoCompanyFound(val message: String = "No company found") : HomeListSideEffect
    data class NoInternetConnection(val message: String = "No internet connection") :
        HomeListSideEffect
}