package com.locotoinnovations.seprecbolivia.ui.view.screen.homeList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.locotoinnovations.core.model.Company
import com.locotoinnovations.seprecbolivia.R
import com.locotoinnovations.seprecbolivia.ui.view.components.CompanyCard

@Composable
fun HomeListScreen(
    homeListViewModel: HomeListViewModel
) {
    val companyList by homeListViewModel.companyList.collectAsState(initial = Company.empty())
    val searchText by homeListViewModel.searchText.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            maxLines = 1,
            placeholder = {
                Text(stringResource(id = R.string.search_for_company))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
            },
            onValueChange = homeListViewModel::onSearchTextChange
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        ) {
            items(companyList.datos?.filas?.size ?: 0) { index ->
                val company = companyList.datos?.filas?.get(index)
                company?.let {
                    CompanyCard(
                        razonSocial = it.razonSocial.orEmpty(),
                        direccion = it.direccion?.codDepartamento?.nombre.orEmpty(),
                        matricula = "temp",
                        estado = it.codEstadoActualizacion?.nombre.orEmpty(),
                        id = it.id.orEmpty(),
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }

}
