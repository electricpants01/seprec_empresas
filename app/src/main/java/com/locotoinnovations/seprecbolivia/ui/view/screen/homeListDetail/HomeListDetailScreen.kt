package com.locotoinnovations.seprecbolivia.ui.view.screen.homeListDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.locotoinnovations.core.model.CompanyDetail
import com.locotoinnovations.seprecbolivia.ui.view.components.CompanyDetailCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeListDetail(
    homeListDetailViewModel: HomeListDetailViewModel,
    id: String,
) {

    LaunchedEffect(key1 = id) {
        homeListDetailViewModel.getCompanyDetail(id)
    }

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

                null -> {
                    // no-op
                }
            }
        }

    }

    LazyColumn {
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

fun <T : Any?> List<T>.detailListToString(): String {
    return this.joinToString(separator = "\n", prefix = "✔ ")
}