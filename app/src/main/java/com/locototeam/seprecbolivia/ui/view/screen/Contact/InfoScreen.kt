package com.locototeam.seprecbolivia.ui.view.screen.Contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locototeam.seprecbolivia.R

@Composable
fun InfoScreen() {

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 96.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.me)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.author)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ClickableText(
            text = AnnotatedString(
                text = stringResource(id = R.string.website),
                spanStyle = SpanStyle(color = Color.Blue, fontSize = 24.sp)
            ), onClick = {
                // open a browser
                uriHandler.openUri("https://locotoinnovations.com")
            })
    }
}