package com.locotoinnovations.seprecbolivia.ui.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TramiteCard(
    title: String,
    matricula: String,
    nombre: String,
    fechaIngreso: String,
    fechaTramite: String,
) {

    val tramiteDetailList = listOf<TramiteCardData>(
        TramiteCardData(title = "Matricula", content = matricula.trim()),
        TramiteCardData(title = "Nombre", content = nombre.trim()),
        TramiteCardData(title = "Fecha de ingreso", content = fechaIngreso.trim()),
        TramiteCardData(title = "Estado de tramite", content = fechaTramite.trim()),
    )

    OutlinedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = title
            )
            Row() {
                Column {
                    tramiteDetailList.forEachIndexed { index, tramiteCardData ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                modifier = Modifier.weight(6 / 12f),
                                fontWeight = FontWeight.Bold,
                                text = tramiteCardData.title
                            )
                            Text(
                                modifier = Modifier.weight(6 / 12f),
                                text = tramiteCardData.content
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class TramiteCardData(
    val title: String,
    val content: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TramiteCardPreview() {
    TramiteCard(
        title = "Tramite 20 - Registro de Comercio",
        matricula = "123456",
        nombre = "Empresa de Prueba",
        fechaIngreso = "01/01/2021",
        fechaTramite = "En proceso"
    )
}