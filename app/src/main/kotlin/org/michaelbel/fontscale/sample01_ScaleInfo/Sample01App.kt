package org.michaelbel.fontscale.sample01_ScaleInfo

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Sample01App(
    innerPadding: PaddingValues
) {
    val fontScale = LocalDensity.current.fontScale
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding + PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "LocalDensity.fontScale = %.2f".format(fontScale),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            Button(
                onClick = { context.startActivity(Intent("android.settings.TEXT_READING_SETTINGS")) }
            ) {
                Text(text = "Открыть настройки размера текста")
            }
        }
    }
}
