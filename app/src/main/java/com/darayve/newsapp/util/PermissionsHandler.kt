package com.darayve.newsapp.util

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionsHandler(
    private val requestPermission: () -> Unit,
    private val application: Application
) {
    fun askForMicrophonePermission() {
        if (!isMicrophonePermissionGranted()) {
            requestPermission.invoke()
        }
    }

    fun isMicrophonePermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
}
