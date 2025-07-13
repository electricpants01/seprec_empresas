package com.locototeam.seprecbolivia.ui.view.screen.homeList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.locototeam.core.model.Company
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.repository.ICompanyService
import com.locototeam.core.retrofit.NetworkResult
import com.locototeam.seprecbolivia.R
import com.locototeam.seprecbolivia.ui.view.MainActivity
import com.locototeam.seprecbolivia.ui.view.components.CompanyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListScreen(
    homeListViewModel: HomeListViewModel = hiltViewModel(),
    navController: NavController,
    isTablet: Boolean,
) {
    val homeListViewState by homeListViewModel.viewState.collectAsStateWithLifecycle()
    var isInitiated = rememberSaveable { false }

    LaunchedEffect(Unit) {
        if (!isInitiated) {
            homeListViewModel.handleEvent(HomeListEvent.InitContent)
        }
        isInitiated = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                modifier = Modifier.padding(
                    start = if (isTablet) 80.dp else 0.dp
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = homeListViewState.searchText,
                maxLines = 1,
                placeholder = {
                    Text(stringResource(id = R.string.search_for_company))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                },
                onValueChange = {
                    homeListViewModel.handleEvent(HomeListEvent.SearchCompany(
                        searchCompanyText = it
                    ))
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
            ) {
                items(homeListViewState.company.datos?.filas?.size ?: 0) { index ->
                    val company = homeListViewState.company.datos?.filas?.get(index)
                    company?.let {
                        CompanyCard(
                            razonSocial = it.razonSocial.orEmpty(),
                            direccion = it.direccion?.codDepartamento?.nombre.orEmpty(),
                            matricula = "temp",
                            estado = it.codEstadoActualizacion?.nombre.orEmpty(),
                            id = it.id.orEmpty(),
                            onCardTapped = {
                                navController.navigate("${MainActivity.ARG_NAVIGATION_SEARCH_DETAIL}/${it.id.orEmpty()}")
                            }
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewHomeListScreen() {
    // Mock NavController
    val mockNavController = rememberNavController()

    // Mock implementation of ICompanyService
    val mockService = object : ICompanyService {
        override suspend fun getCompany(searchCompany: String): NetworkResult {
            return NetworkResult.NetworkSuccess(Company.sample())
        }

        override suspend fun getCompanyDetail(companyId: String): NetworkResult {
            return NetworkResult.NetworkSuccess(Company.sample())
        }

        override suspend fun getTramiteDetail(tramiteId: String): NetworkResult {
            return NetworkResult.NetworkSuccess(Company.sample())
        }
    }

    // Mocking the repository with the mocked service
    val mockRepository = CompanyRepository(mockService)

    val mockListViewModel = HomeListViewModel(mockRepository)

    // Preview HomeListScreen
    HomeListScreen(
        homeListViewModel = mockListViewModel,
        navController = mockNavController,
        isTablet = false,
    )
}
