package com.locototeam.seprecbolivia.ui.view.screen.tramite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.locototeam.core.model.TramiteDetail
import com.locototeam.seprecbolivia.R
import com.locototeam.seprecbolivia.formatDate
import com.locototeam.seprecbolivia.toDate
import com.locototeam.seprecbolivia.ui.view.components.TramiteCard
import com.locototeam.seprecbolivia.ui.view.components.TramiteTrackCard
import com.locototeam.seprecbolivia.ui.view.components.TramiteTrackCardData

@Composable
fun TramiteScreen(
    tramiteViewModel: TramiteViewModel = hiltViewModel(),
) {

    val tramiteDetailUiState by tramiteViewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = tramiteDetailUiState.searchText,
            maxLines = 1,
            minLines = 1,
            placeholder = {
                Text(stringResource(id = R.string.tramite_search_bar))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
            },
            onValueChange = tramiteViewModel::onSearchTextChange
        )
        Spacer(modifier = Modifier.padding(8.dp))
        val tramiteDetail = tramiteDetailUiState.tramiteDetail
        if (tramiteDetail != TramiteDetail.empty()) {
            TramiteCard(
                title = tramiteDetail.datos?.nombre?.nombre.orEmpty(),
                matricula = tramiteDetail.datos?.empresa?.matricula.orEmpty(),
                nombre = tramiteDetail.datos?.empresa?.razonSocial.orEmpty(),
                fechaIngreso = tramiteDetail.datos?.fechaCreacion.orEmpty(),
                fechaTramite = tramiteDetail.datos?.estado.orEmpty()
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.tramite_track)
            )
            tramiteDetail.datos?.bitacoras?.map {
                TramiteTrackCardData(
                    it?.operacion.orEmpty(),
                    it?.fecha?.toDate()?.formatDate().orEmpty()
                )
            }
                ?.let { tramiteTrackDataList -> TramiteTrackCard(tramiteTrackList = tramiteTrackDataList) }
        }

    }
}