@file:OptIn(ExperimentalLayoutApi::class)

package org.michaelbel.fontscale.sample08_ScalableContent

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Composable
fun Sample08App(
    innerPadding: PaddingValues
) {
    val currentDensity = LocalDensity.current
    var scaleFactor by rememberSaveable { mutableFloatStateOf(1F) }
    var isDensityScaling by rememberSaveable { mutableStateOf(true) }
    val transformableState = rememberTransformableState { _, zoomChange, _, _ ->
        scaleFactor = (scaleFactor * zoomChange).coerceIn(.75F, 3.5F)
    }
    val scaledDensity = remember(currentDensity.density, currentDensity.fontScale, scaleFactor, isDensityScaling) {
        Density(
            density = currentDensity.density,
            fontScale = currentDensity.fontScale * scaleFactor
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding + PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CompositionLocalProvider(
                LocalDensity provides scaledDensity
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformable(state = transformableState),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Доступный контент",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "При увеличении этот текст переносится на новые строки, а карточка растёт по высоте. В режиме всей плотности вместе с текстом меняются отступы и элементы управления.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Start
                            )
                        )

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Пример действия"
                            )
                        }
                    }
                }
            }
        }
    }
}
