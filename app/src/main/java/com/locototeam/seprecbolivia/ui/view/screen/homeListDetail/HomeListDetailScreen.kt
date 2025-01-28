package com.locototeam.seprecbolivia.ui.view.screen.homeListDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.locototeam.core.model.CompanyDetail
import com.locototeam.core.repository.CompanyRepository
import com.locototeam.core.repository.ICompanyService
import com.locototeam.core.retrofit.NetworkResult
import com.locototeam.seprecbolivia.ui.view.MainActivity
import com.locototeam.seprecbolivia.ui.view.components.CompanyDetailCard
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListDetail(
    homeListDetailViewModel: HomeListDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val context: Context = LocalContext.current
    val companyDetail by homeListDetailViewModel.companyDetail.collectAsState(CompanyDetail.empty())

    LaunchedEffect(homeListDetailViewModel.event) {
        homeListDetailViewModel.event.collectLatest { event ->
            when (event) {
                is HomeListDetailViewModel.HomeListDetailEvent.DetailNotFound -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is HomeListDetailViewModel.HomeListDetailEvent.NetworkError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                null -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = companyDetail.datos?.razonSocial.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                companyDetail.datos?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = it.razonSocial.orEmpty()
                    )
                    CompanyDetailCard(
                        matriculaComercio = it.matricula.orEmpty(),
                        matriculaAnterior = it.matriculaAnterior.orEmpty(),
                        estado = it.estado.orEmpty(),
                        actividad = it.objetos_sociales?.joinToString(
                            separator = "\n",
                        ) {
                            it?.let {
                                "✔ ${it.objetoSocial}"
                            }.orEmpty()
                        }.orEmpty(),
                        tipoDeSocietario = it.codTipoUnidadEconomica?.nombre.orEmpty(),
                        domicilio = it.direccion?.domicilio.orEmpty().plus(": ")
                            .plus(it.direccion?.direccionCompleta.orEmpty()),
                        contacto = it.contactos?.descripcion?.joinToString(
                            separator = "\n",
                        ) {
                            it?.let {
                                "✔ ${it.tipo.orEmpty()}: ${it.numero}"
                            }.orEmpty()
                        }.orEmpty(),
                        correo = it.correos?.descripcion?.joinToString(
                            separator = "\n",
                        ) {
                            it?.let {
                                "✔ ${it.tipo.orEmpty()}: ${it.correo.orEmpty()}"
                            }.orEmpty()
                        }
                            .orEmpty(),
                        actividadesEconomicas = it.actividadesEconomicas?.joinToString {
                            it?.joinToString(
                                separator = "\n",
                            ) { it?.let { "✔ ${it.descripcion}" }.orEmpty() }.orEmpty()
                        }.orEmpty(),
                    )

                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewHomeListDetail() {
    // Mock SavedStateHandle with the necessary argument
    val mockSavedStateHandle = SavedStateHandle(
        mapOf(MainActivity.ARG_NAVIGATION_SEARCH_ID to "mockCompanyId")
    )

    // Mock implementation of ICompanyService
    val mockService = object : ICompanyService {
        override suspend fun getCompany(searchCompany: String): NetworkResult {
            return NetworkResult.NetworkSuccess(CompanyDetail.sample())
        }

        override suspend fun getCompanyDetail(companyId: String): NetworkResult {
            return NetworkResult.NetworkSuccess(CompanyDetail.sample())
        }

        override suspend fun getTramiteDetail(tramiteId: String): NetworkResult {
            return NetworkResult.NetworkSuccess(CompanyDetail.sample())
        }
    }

    // Mocking the repository with the mocked service
    val mockRepository = CompanyRepository(mockService)

    // Mock ViewModel
    val mockViewModel = HomeListDetailViewModel(
        savedStateHandle = mockSavedStateHandle,
        companyRepository = mockRepository,
    )

    // Preview the composable
    HomeListDetail(
        homeListDetailViewModel = mockViewModel,
        onBackClick = { }
    )
}