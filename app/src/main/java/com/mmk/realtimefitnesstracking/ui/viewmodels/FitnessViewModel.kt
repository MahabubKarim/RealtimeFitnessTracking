package com.mmk.realtimefitnesstracking.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.realtimefitnesstracking.sensor.FitnessSensorManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val sensorManager: FitnessSensorManager
) : ViewModel() {

    // Step tracking
    val steps = MutableStateFlow(0)
    val walkingSpeed = MutableStateFlow(0.0) // m/s
    val stepFrequency = MutableStateFlow(0.0) // steps/min
    //val heartRate = sensorManager.heartRateFlow
    // Heart rate tracking
    val heartRate = MutableStateFlow<Int?>(null)
    val lastHeartRateUpdateTime = MutableStateFlow<Long?>(null)
    val accelerometerData = sensorManager.accelFlow

    private var startTime: Long = 0L
    private val strideLength = 0.7f // Average stride length in meters

    fun startTracking() {
        sensorManager.start()
        startTime = System.currentTimeMillis()

        viewModelScope.launch {
            // Collect step count updates
            sensorManager.sensorStepCount.collect { stepCount ->
                steps.value = stepCount
                calculateDerivedMetrics()
            }
        }

        // Collect heart rate updates
        viewModelScope.launch {
            sensorManager.heartRateFlow.collect { hr ->
                heartRate.value = hr
                lastHeartRateUpdateTime.value = System.currentTimeMillis()

                // Optional: Log or process heart rate data
                if (hr != null) {
                    Log.d("HeartRate", "New reading: $hr BPM")
                    // You could add additional processing here
                }
            }
        }

        // Optional: Collect accelerometer data for additional processing
        viewModelScope.launch {
            sensorManager.accelFlow.collect { accelData ->

            }
        }
    }

    private fun calculateDerivedMetrics() {
        val timeElapsed = (System.currentTimeMillis() - startTime) / 1000.0 // in seconds

        if (timeElapsed > 0) {
            // Calculate speed in m/s (distance/time)
            walkingSpeed.value = (steps.value * strideLength) / timeElapsed

            // Calculate step frequency (steps per minute)
            stepFrequency.value = (steps.value / timeElapsed) * 60
        }
    }

    fun stopTracking() {
        sensorManager.stop()
        resetMetrics()
    }

    private fun resetMetrics() {
        steps.value = 0
        walkingSpeed.value = 0.0
        stepFrequency.value = 0.0
        heartRate.value = null
        lastHeartRateUpdateTime.value = null
    }

    // Helper function to check if heart rate is recent (within last 10 seconds)
    fun isHeartRateRecent(): Boolean {
        return lastHeartRateUpdateTime.value?.let { lastUpdate ->
            System.currentTimeMillis() - lastUpdate < 10000 // 10 seconds
        } ?: false
    }

    // Helper function to format speed for display
    fun getFormattedSpeed(): String {
        return "%.1f".format(walkingSpeed.value * 3.6) // Convert m/s to km/h
    }

    // Helper function to format step frequency
    fun getFormattedStepFrequency(): String {
        return "%.0f".format(stepFrequency.value)
    }
}