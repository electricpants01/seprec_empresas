package com.locotoinnovations.seprecbolivia.ui.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CompanyDetailCard(
    matriculaComercio: String,
    matriculaAnterior: String,
    estado: String,
    actividad: String,
    tipoDeSocietario: String,
    domicilio: String,
    contacto: String,
    correo: String,
    actividadesEconomicas: String,
) {

    val detailList = listOf(
        CompanyDetailData(title = "Matricula Comercio", content = matriculaComercio.trim()),
        CompanyDetailData(title = "Matricula Anterior", content = matriculaAnterior.trim()),
        CompanyDetailData(title = "Estado", content = estado.trim()),
        CompanyDetailData(title = "Actividad", content = actividad.trim()),
        CompanyDetailData(title = "Tipo de Societario", content = tipoDeSocietario.trim()),
        CompanyDetailData(title = "Domicilio", content = domicilio.trim()),
        CompanyDetailData(title = "Contacto", content = contacto.trim()),
        CompanyDetailData(title = "Correo", content = correo.trim()),
        CompanyDetailData(title = "Actividades Economicas", content = actividadesEconomicas.trim()),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        detailList.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier.weight(3 / 12f),
                    text = it.title,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1 / 12f))
                Text(
                    modifier = Modifier.weight(8 / 12f),
                    text = it.content,
                    color = Color.Black
                )
            }
        }
    }
}

private data class CompanyDetailData(
    val title: String,
    val content: String
)