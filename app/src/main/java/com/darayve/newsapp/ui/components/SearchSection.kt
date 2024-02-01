package com.darayve.newsapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darayve.newsapp.util.SpeechToTextParser

// TODO: Consertar bug do BasicTextField quando o stt é usado
// TODO: Alterar futuramente o text field para conter placeholder etc
@Composable
fun SearchSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onMicClick: () -> Unit,
    speechToTextParser: SpeechToTextParser
) {
    val speechState by speechToTextParser.speechState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            value = speechState.spokenText.ifEmpty { searchQuery },
            onValueChange = { query ->
                onSearchQueryChange(query)
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
        )
        IconButton(
            onClick = {
                onMicClick()
            }
        ) {
            AnimatedContent(targetState = speechState.isSpeaking, label = "") { isSpeaking ->
                if (isSpeaking) {
                    Icon(
                        imageVector = Icons.Default.MicOff,
                        contentDescription = "Stop voice search"
                    )
                } else {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Search by voice")
                }
            }

        }
    }

    LaunchedEffect(speechState.spokenText) {
        if (speechState.spokenText.isNotEmpty()) {
            onSearchQueryChange(speechState.spokenText)
        }
    }

    DisposableEffect(speechState.isSpeaking) {
        onDispose {
            if (!speechState.isSpeaking) {
                onSearchQueryChange("")
            }
        }
    }
}