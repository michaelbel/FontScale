package org.michaelbel.fontscale.sample06_FontScaleLimit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.michaelbel.fontscale.R
import kotlin.math.min

@Composable
fun Sample06App(
    innerPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding + PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FontScaleLimit(
                maxFontScale = 1.15F
            ) {
                Text(
                    text = "Этот блок ограничивает рост текста до 1.15F через CompositionLocalProvider. Это помогает сохранить рабочий UI.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        item {
            FontScaleLimit(
                maxFontScale = 1.15F
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .widthIn(max = 320.dp)
                        .heightIn(min = 48.dp)
                ) {
                    Text(
                        text = "Текст внутри ограниченной density",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun FontScaleLimit(
    maxFontScale: Float,
    content: @Composable () -> Unit
) {
    val base = LocalDensity.current
    val limitedDensity = remember(base.density, base.fontScale, maxFontScale) {
        Density(
            density = base.density,
            fontScale = min(base.fontScale, maxFontScale)
        )
    }

    CompositionLocalProvider(
        LocalDensity provides limitedDensity,
        content = content
    )
}
