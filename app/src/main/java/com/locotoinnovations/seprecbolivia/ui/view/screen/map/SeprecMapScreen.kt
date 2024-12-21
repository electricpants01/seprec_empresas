package com.locotoinnovations.seprecbolivia.ui.view.screen.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.locotoinnovations.seprecbolivia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeprecMapScreen() {

    val markerList = listOf(
        // Santa Cruz de la Sierra
        SeprecMarkerData(
            LatLng(-17.774221417256665, -63.17967653788192),
            "Seprec Central - Santa Cruz",
            "Noreste, Santa Cruz, Av. Uruguay esquina, Santa Cruz de la Sierra"
        ),
        SeprecMarkerData(
            LatLng(-17.76004396206579, -63.20012148813888),
            "Seprec Zona Norte - Santa Cruz",
            "Av. San Martin y, Santa Cruz de la Sierra"
        ),
        SeprecMarkerData(
            LatLng(-17.341073509653057, -63.25513659553614),
            "Seprec Santa Cruz Montero",
            "C. Litoral, Montero"
        ),
        // la paz
        SeprecMarkerData(
            LatLng(-16.497667, -68.164583),
            "Seprec La paz - El alto",
            "Avenida Panorámica, Estación Jach´a Qhathu, Línea Roja Mi Teleférico, local comercial RJ-JQ-COM-LOC-3"
        ),
        SeprecMarkerData(
            LatLng(-16.541167, -68.08825),
            "Seprec La paz - Zona Sur",
            "Avenida Sánchez Bustamante, esquina calle 11 de Calacoto"
        ),
        // Cochabamba
        SeprecMarkerData(
            LatLng(-17.384944, -66.157278),
            "Seprec Central - Cochambamba",
            "Avenida José Ballivián (El Prado), entre calles La Paz y Chuquisaca, edificio Ave Paraíso Nº 688"
        ),
        SeprecMarkerData(
            LatLng(-17.396833, -66.279028),
            "Seprec Cochabamba - Quillacollo",
            "Calle Pacheco, entre Nathaniel Aguirre y 14 de Septiembre, edificio IBM, piso 3"
        ),
        // Potosi
        SeprecMarkerData(
            LatLng(-19.581917, -65.757972),
            "Seprec Potosi",
            "Avenida Villazón, entre calles San Alberto y Wenceslao Alba, edificio Salinas Nº 242"
        ),
        // Sucre
        SeprecMarkerData(
            LatLng(-19.046611, -65.260139),
            "Seprec Sucre",
            "Calle General Arenales 17-19 entre Calles Junín y Aniceto Arce, Zona Central de la ciudad de Sucre - Chuquisaca"
        ),
        // Tarija
        SeprecMarkerData(
            LatLng(-21.540028, -64.724806),
            "Seprec Tarija",
            "Avenida La Paz, entre calles Ciro Trigo y Avaroa, edificio Santa Clara Nº 243, zona Villa Fátima"
        ),
    )

    var centerMap by remember {
        mutableStateOf(markerList.first())
    }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            cameraPositionState = CameraPositionState(
                position = CameraPosition(
                    centerMap.latLng,
                    12f,
                    0f,
                    0f
                )
            ),
        ) {
            markerList.forEach { markerPosition ->
                Marker(
                    position = markerPosition.latLng,
                    title = markerPosition.title,
                    snippet = markerPosition.snippet
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                isSheetOpen = true
            },
            content = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Add location",
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        text = stringResource(id = R.string.select_location)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[0]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "Santa Cruz"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[5]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "Cochabamba"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[3]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "La Paz"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[7]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "Potosí"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[8]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "Sucre"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                centerMap = markerList[9]
                                isSheetOpen = false
                            },
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        text = "Tarija"
                    )
                }
            }
        }
    }
}

private data class SeprecMarkerData(
    val latLng: LatLng,
    val title: String,
    val snippet: String
)