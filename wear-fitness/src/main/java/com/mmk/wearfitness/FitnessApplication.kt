package com.mmk.wearfitness

import android.app.Application
import com.mmk.wearfitness.services.SensorForegroundService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApplication : Application() {
    fun ambientModeChanged(isAmbient: Boolean) {
        // Handle application-level ambient mode changes
        SensorForegroundService().setLowPowerMode(isAmbient)
    }

    fun updateAmbientData() {
        // Update data specifically for ambient mode
    }
}