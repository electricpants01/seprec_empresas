package com.locotoinnovations.seprecbolivia.ui.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompanyCard(
    razonSocial: String,
    direccion: String,
    matricula: String,
    estado: String,
    id: String
) {
    val titleList = listOf<CompanyCardData>(
        CompanyCardData(title = "Razón Social", content = razonSocial.trim()),
        CompanyCardData(title = "Dirección", content = direccion.trim()),
        CompanyCardData(title = "Matricula", content = matricula.trim()),
        CompanyCardData(title = "Estado", content = estado.trim()),
        CompanyCardData(title = "ID", content = id.trim()),
    )
    OutlinedCard(
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            titleList.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.weight(3/12f),
                        text = it.title,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1/12f))
                    Text(
                        modifier = Modifier.weight(8/12f),
                        text = it.content,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

private data class CompanyCardData(
    val title: String,
    val content: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompanyCardPreview() {
    CompanyCard(
        razonSocial = "Razon Social",
        direccion = "Direccion",
        matricula = "Matricula",
        estado = "Estado",
        id = "ID"
    )
}