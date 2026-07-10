package org.michaelbel.fontscale.sample02_SizeIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.michaelbel.fontscale.R

@Composable
fun Sample02App(
    innerPadding: PaddingValues
) {
    val initialValue = "Большой системный шрифт делает поле выше. Если ограничить TextField фиксированной высотой, ввод быстро станет неудобным."
    var text by rememberSaveable { mutableStateOf(initialValue) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding + PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Кнопки, поля ввода и карточки не должны иметь жесткую высоту, если внутри есть текст. Минимальную высоту задавай через heightIn, а контенту оставляй место расти.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 112.dp),
                label = { Text(text = "Комментарий") },
                minLines = 3
            )
        }
        item {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text(
                    text = "Кнопка с длинным текстом и гибкой высотой",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
