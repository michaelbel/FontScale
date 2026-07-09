@file:OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package org.michaelbel.fontscale

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.michaelbel.fontscale.ui.AppTheme
import kotlin.math.min

@Composable
fun MainActivityContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { CurrentScaleSection() }
            item { FlexibleContainersSection() }
            item { FlowRowSection() }
            item { StableRowSection() }
            item { OneLineTextSection() }
            item { LimitedFontScaleSection() }
        }
    }
}

@Composable
private fun CurrentScaleSection() {
    val fontScale = LocalDensity.current.fontScale

    SectionCard(
        title = stringResource(R.string.section_current_title)
    ) {
        Text(
            text = stringResource(R.string.current_font_scale_value, fontScale),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.current_font_scale_hint),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun FlexibleContainersSection() {
    val initialValue = stringResource(R.string.text_field_initial)
    var text by rememberSaveable { mutableStateOf(initialValue) }

    SectionCard(
        title = stringResource(R.string.section_flexible_title)
    ) {
        Text(
            text = stringResource(R.string.section_flexible_body),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Text(
                text = stringResource(R.string.primary_action),
                textAlign = TextAlign.Center
            )
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 112.dp),
            label = {
                Text(
                    text = stringResource(R.string.text_field_label)
                )
            },
            minLines = 3,
            maxLines = 5
        )
    }
}

@Composable
private fun FlowRowSection() {
    val actions = listOf(
        stringResource(R.string.flow_action_save),
        stringResource(R.string.flow_action_share),
        stringResource(R.string.flow_action_export),
        stringResource(R.string.flow_action_archive)
    )
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    SectionCard(
        title = stringResource(R.string.section_flow_title)
    ) {
        Text(
            text = stringResource(R.string.section_flow_body),
            style = MaterialTheme.typography.bodyMedium
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            actions.forEachIndexed { index, action ->
                FilterChip(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    label = {
                        Text(
                            text = action,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun StableRowSection() {
    SectionCard(
        title = stringResource(R.string.section_row_title)
    ) {
        Text(
            text = stringResource(R.string.section_row_body),
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.row_long_text),
                modifier = Modifier.weight(1f, fill = false),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.row_badge),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun OneLineTextSection() {
    SectionCard(
        title = stringResource(R.string.section_marquee_title)
    ) {
        Text(
            text = stringResource(R.string.section_marquee_body),
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.marquee_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun LimitedFontScaleSection() {
    SectionCard(
        title = stringResource(R.string.section_limited_title)
    ) {
        Text(
            text = stringResource(R.string.section_limited_body),
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider()
        FontScaleLimit(
            maxFontScale = 1.15f
        ) {
            val limitedFontScale = LocalDensity.current.fontScale

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.limited_font_scale_value, limitedFontScale),
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .widthIn(max = 320.dp)
                        .heightIn(min = 48.dp)
                ) {
                    Text(
                        text = stringResource(R.string.limited_action),
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

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            content()
        }
    }
}

@Preview(
    name = "fontScale 1.5",
    showBackground = true,
    widthDp = 411,
    fontScale = 1.5f
)
@Composable
private fun MainActivityContentFontScale150Preview() {
    AppTheme {
        MainActivityContent()
    }
}

@Preview(
    name = "fontScale 2.0",
    showBackground = true,
    widthDp = 411,
    fontScale = 2.0f
)
@Composable
private fun MainActivityContentFontScale200Preview() {
    AppTheme {
        MainActivityContent()
    }
}
