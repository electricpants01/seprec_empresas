package com.locotoinnovations.seprecbolivia.ui.view.screen.homeListDetail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.locotoinnovations.core.model.CompanyDetail
import com.locotoinnovations.seprecbolivia.ui.view.components.CompanyDetailCard

@Composable
fun HomeListDetail(
    homeListDetailViewModel: HomeListDetailViewModel,
    id: String,
) {

    LaunchedEffect(key1 = id) {
        homeListDetailViewModel.getCompanyDetail(id)
    }

    val companyDetail by homeListDetailViewModel.companyDetail.collectAsState(CompanyDetail.empty())

    LazyColumn {
        item {
            companyDetail.datos?.let {
                Text(text = companyDetail.datos?.razonSocial.orEmpty())
                CompanyDetailCard(
                    matriculaComercio = it.matricula.orEmpty(),
                    matriculaAnterior = it.matriculaAnterior.orEmpty(),
                    estado = it.estado.orEmpty(),
                    actividad = it.objetos_sociales?.detailListToString().orEmpty(),
                    tipoDeSocietario = it.codTipoUnidadEconomica?.nombre.orEmpty(),
                    domicilio = it.direccion?.domicilio.orEmpty().plus(" ")
                        .plus(it.direccion?.direccionCompleta.orEmpty()),
                    contacto = it.contactos?.descripcion?.detailListToString().orEmpty(),
                    correo = it.correos?.descripcion?.detailListToString().orEmpty(),
                    actividadesEconomicas = it.actividadesEconomicas?.detailListToString().orEmpty()
                )

            }
        }
    }
}

fun <T : Any?> List<T>.detailListToString(): String {
    return this.joinToString(separator = "\n", prefix = "✔ ")
}