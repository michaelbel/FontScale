@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.fontscale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.michaelbel.fontscale.sample01_ScaleInfo.Sample01App
import org.michaelbel.fontscale.sample02_SizeIn.Sample02App
import org.michaelbel.fontscale.sample03_FlowRow.Sample03App
import org.michaelbel.fontscale.sample04_WeightFill.Sample04App
import org.michaelbel.fontscale.sample05_BasicMarquee.Sample05App
import org.michaelbel.fontscale.sample06_FontScaleLimit.Sample06App
import org.michaelbel.fontscale.sample07_TextAutoSize.Sample07App
import org.michaelbel.fontscale.sample08_ScalableContent.Sample08App

private data object Home
private data object Sample01
private data object Sample02
private data object Sample03
private data object Sample04
private data object Sample05
private data object Sample06
private data object Sample07
private data object Sample08

@Composable
fun MainActivityContent() {
    val systemDensity = LocalDensity.current
    var fontScale by rememberSaveable { mutableFloatStateOf(systemDensity.fontScale) }
    val backStack = remember { mutableStateListOf<Any>(Home) }
    val onFontScaleChange: (Float) -> Unit = { fontScale = it }
    val hapticFeedback = LocalHapticFeedback.current

    val scaledDensity = remember(systemDensity.density, fontScale) {
        Density(density = systemDensity.density, fontScale = fontScale)
    }

    val topBarDensity = remember(systemDensity.density) {
        Density(density = systemDensity.density, fontScale = 1F)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    CompositionLocalProvider(LocalDensity provides scaledDensity) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CompositionLocalProvider(
                    LocalDensity provides topBarDensity
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (backStack.lastOrNull()) {
                                    Sample01 -> "ScaleInfo"
                                    Sample02 -> "SizeIn"
                                    Sample03 -> "FlowRow"
                                    Sample04 -> "WeightFill"
                                    Sample05 -> "BasicMarquee"
                                    Sample06 -> "FontScaleLimit"
                                    Sample07 -> "TextAutoSize"
                                    Sample08 -> "ScalableContent"
                                    else -> stringResource(R.string.app_name)
                                }
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            bottomBar = {
                CompositionLocalProvider(
                    LocalDensity provides topBarDensity
                ) {
                    BottomAppBar {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilledTonalIconButton(
                                onClick = {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.VirtualKey)
                                    onFontScaleChange((fontScale - .1F).coerceIn(1F, 2F))
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_remove_24),
                                    contentDescription = null
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "LocalDensity.fontScale = %.2f".format(fontScale),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )

                                Slider(
                                    value = fontScale,
                                    onValueChange = onFontScaleChange,
                                    valueRange = 1F..2F,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }

                            FilledTonalIconButton(
                                onClick = {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.VirtualKey)
                                    onFontScaleChange((fontScale + .1F).coerceIn(1F, 2F))
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_24),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Home> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = innerPadding + PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                        ) {
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample01) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 0, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 01") },
                                    content = { Text(text = "ScaleInfo") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample02) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 1, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 02") },
                                    content = { Text(text = "SizeIn") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample03) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 2, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 03") },
                                    content = { Text(text = "FlowRow") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample04) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 3, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 04") },
                                    content = { Text(text = "WeightFill") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample05) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 4, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 05") },
                                    content = { Text(text = "BasicMarquee") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample06) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 5, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 06") },
                                    content = { Text(text = "FontScaleLimit") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample07) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 6, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 07") },
                                    content = { Text(text = "TextAutoSize") }
                                )
                            }
                            item {
                                SegmentedListItem(
                                    onClick = { backStack.add(Sample08) },
                                    shapes = ListItemDefaults.segmentedShapes(index = 7, count = 8),
                                    colors = ListItemDefaults.segmentedColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                                    ),
                                    overlineContent = { Text(text = "Sample 08") },
                                    content = { Text(text = "ScalableContent") }
                                )
                            }
                        }
                    }
                    entry<Sample01> { Sample01App(innerPadding) }
                    entry<Sample02> { Sample02App(innerPadding) }
                    entry<Sample03> { Sample03App(innerPadding) }
                    entry<Sample04> { Sample04App(innerPadding) }
                    entry<Sample05> { Sample05App(innerPadding) }
                    entry<Sample06> { Sample06App(innerPadding) }
                    entry<Sample07> { Sample07App(innerPadding) }
                    entry<Sample08> { Sample08App(innerPadding) }
                }
            )
        }
    }
}
