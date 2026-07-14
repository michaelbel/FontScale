@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.fontscale

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableStateOf
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
import org.michaelbel.fontscale.sample01_ScaleInfo.Sample01App
import org.michaelbel.fontscale.sample02_SizeIn.Sample02App
import org.michaelbel.fontscale.sample03_FlowRow.Sample03App
import org.michaelbel.fontscale.sample04_WeightFill.Sample04App
import org.michaelbel.fontscale.sample05_BasicMarquee.Sample05App
import org.michaelbel.fontscale.sample06_FontScaleLimit.Sample06App
import org.michaelbel.fontscale.sample07_TextAutoSize.Sample07App
import org.michaelbel.fontscale.sample08_ScalableContent.Sample08App

@Composable
fun MainActivityContent() {
    val systemDensity = LocalDensity.current
    var fontScale by rememberSaveable { mutableFloatStateOf(systemDensity.fontScale) }
    var selectedSample by rememberSaveable { mutableStateOf<Int?>(null) }
    val onFontScaleChange: (Float) -> Unit = { fontScale = it }
    val hapticFeedback = LocalHapticFeedback.current

    BackHandler(enabled = selectedSample != null) {
        selectedSample = null
    }

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
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                CompositionLocalProvider(
                    LocalDensity provides topBarDensity
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (selectedSample) {
                                    0 -> "ScaleInfo"
                                    1 -> "SizeIn"
                                    2 -> "FlowRow"
                                    3 -> "WeightFill"
                                    4 -> "BasicMarquee"
                                    5 -> "FontScaleLimit"
                                    6 -> "TextAutoSize"
                                    7 -> "ScalableContent"
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
            }
        ) { innerPadding ->
            when (selectedSample) {
                null -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding + PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 0 },
                            shapes = ListItemDefaults.segmentedShapes(index = 0, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "ScaleInfo")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 1 },
                            shapes = ListItemDefaults.segmentedShapes(index = 1, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "SizeIn")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 2 },
                            shapes = ListItemDefaults.segmentedShapes(index = 2, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "FlowRow")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 3 },
                            shapes = ListItemDefaults.segmentedShapes(index = 3, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "WeightFill")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 4 },
                            shapes = ListItemDefaults.segmentedShapes(index = 4, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "BasicMarquee")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 5 },
                            shapes = ListItemDefaults.segmentedShapes(index = 5, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "FontScaleLimit")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 6 },
                            shapes = ListItemDefaults.segmentedShapes(index = 6, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "TextAutoSize")
                        }
                    }

                    item {
                        SegmentedListItem(
                            onClick = { selectedSample = 7 },
                            shapes = ListItemDefaults.segmentedShapes(index = 7, count = 8),
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            )
                        ) {
                            Text(text = "ScalableContent")
                        }
                    }
                }
                0 -> Sample01App(innerPadding)
                1 -> Sample02App(innerPadding)
                2 -> Sample03App(innerPadding)
                3 -> Sample04App(innerPadding)
                4 -> Sample05App(innerPadding)
                5 -> Sample06App(innerPadding)
                6 -> Sample07App(innerPadding)
                7 -> Sample08App(innerPadding)
            }
        }
    }
}
