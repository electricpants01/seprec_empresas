package com.locototeam.seprecbolivia.ui.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locototeam.seprecbolivia.R

@Composable
fun TramiteTrackCard(
    tramiteTrackList: List<TramiteTrackCardData>
) {
    OutlinedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1 / 2f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.tramite_track_state)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1 / 2f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.tramite_track_date)
                )
            }
            Row() {
                Column {
                    tramiteTrackList.forEachIndexed { index, track ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.weight(1 / 2f),
                                textAlign = TextAlign.Center,
                                text = track.estado
                            )
                            Text(
                                modifier = Modifier.weight(1 / 2f),
                                textAlign = TextAlign.Center,
                                text = track.fechaEstado
                            )
                        }
                    }
                }
            }
        }
    }
}

data class TramiteTrackCardData(
    val estado: String,
    val fechaEstado: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TramiteHistoryCardPreview() {
    TramiteTrackCard(
        tramiteTrackList = listOf(
            TramiteTrackCardData(estado = "Estado", fechaEstado = "Fecha"),
            TramiteTrackCardData(estado = "Estado", fechaEstado = "Fecha"),
            TramiteTrackCardData(estado = "Estado", fechaEstado = "Fecha"),
            TramiteTrackCardData(estado = "Estado", fechaEstado = "Fecha"),
            TramiteTrackCardData(estado = "Estado", fechaEstado = "Fecha"),
        )
    )
}