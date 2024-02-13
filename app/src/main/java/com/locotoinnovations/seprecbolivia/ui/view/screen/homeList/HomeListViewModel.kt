package com.locotoinnovations.seprecbolivia.ui.view.screen.homeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotoinnovations.core.model.Company
import com.locotoinnovations.core.repository.CompanyRepository
import com.locotoinnovations.core.retrofit.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
): ViewModel() {

    private val _event = MutableStateFlow<HomeListEvent?>(null)
    private val _companyList: MutableStateFlow<Company> = MutableStateFlow(Company.empty())
    val companyList = _companyList.asStateFlow()
    // first state the user is searching
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        getCompanyList("ypfb")
    }

    private fun getCompanyList(searchCompanyText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = companyRepository.getCompany(searchCompanyText)
            withContext(Dispatchers.Main) {
                when(result) {
                    is NetworkResult.NetworkSuccess<*> -> {
                        // emit company list
                        _companyList.value = result.data as Company
                    }
                    is NetworkResult.NetworkEmptyResult<*> -> {
                        _event.emit(HomeListEvent.ShowError("No company found"))
                    }
                    is NetworkResult.NetworkFailure -> {
                        _event.emit(HomeListEvent.ShowError("Network error"))
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        println("chris text ${text}")
        _searchText.value = text
        getCompanyList(text)
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    sealed class HomeListEvent {
        data class ShowError(val message: String) : HomeListEvent()
    }
}