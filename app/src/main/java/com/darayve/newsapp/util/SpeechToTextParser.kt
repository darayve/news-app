package com.darayve.newsapp.util

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeechToTextParser(
    private val application: Application
) : RecognitionListener {

    private val _speechState = MutableStateFlow(SpeechToTextState())
    val speechState = _speechState.asStateFlow()

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(application)

    fun startListening(languageCode: String = "en") {
        _speechState.update { SpeechToTextState() }

        if (!isVoiceRecordPermissionGranted()) {
            _speechState.update { state ->
                state.copy(error = "Speech-to-text permission not granted.")
            }
            return
        }
        checkRecognitionAvailability()

        recognizer.setRecognitionListener(this)
        recognizer.startListening(getRecognizerIntent(languageCode))

        _speechState.update { state -> state.copy(isSpeaking = true) }
    }

    private fun checkRecognitionAvailability() {
        if (!SpeechRecognizer.isRecognitionAvailable(application)) {
            _speechState.update { state ->
                state.copy(error = "Recognition is not available.")
            }
        }
    }

    fun stopListening() {
        _speechState.update { state ->
            state.copy(isSpeaking = false)
        }
        recognizer.stopListening()
    }

    private fun isVoiceRecordPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            application, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

    private fun getRecognizerIntent(languageCode: String) =
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

    override fun onReadyForSpeech(p0: Bundle?) {
        _speechState.update { state ->
            state.copy(
                error = null
            )
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _speechState.update { state ->
            state.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(error: Int) {
        _speechState.update { state ->
            if (error == SpeechRecognizer.ERROR_CLIENT) return
            state.copy(error = "Error: $error")
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { textResult ->
                _speechState.update { state ->
                    state.copy(spokenText = textResult, isSpeaking = false)
                }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}
