package com.locotoinnovations.seprecbolivia.ui.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng

@Composable
private fun BottomSheetContent(
    onItemAdded: (LatLng) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Add your bottom sheet components here, e.g.,
        Text("Add a new location:")
        // Input fields for user interaction
        // Button to trigger item addition with onItemAdded callback
    }
}