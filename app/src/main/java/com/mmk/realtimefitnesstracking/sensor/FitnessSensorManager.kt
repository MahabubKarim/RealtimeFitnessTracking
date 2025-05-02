package com.mmk.realtimefitnesstracking.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FitnessSensorManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val androidSensorManager: SensorManager
) : SensorEventListener {

    // Sensor tracking flows
    private val _sensorStepCount = MutableStateFlow(0)
    val sensorStepCount: Flow<Int> = _sensorStepCount

    private val _heartRateFlow = MutableStateFlow<Int?>(null)
    val heartRateFlow: Flow<Int?> = _heartRateFlow

    private val _accelFlow = MutableStateFlow<AccelData?>(null)
    val accelFlow: Flow<AccelData?> = _accelFlow

    private var initialSensorStepCount: Float? = null

    fun start() {
        resetTrackingData()
        registerSensors()
    }

    fun stop() {
        androidSensorManager.unregisterListener(this)
        resetTrackingData()
    }

    private fun resetTrackingData() {
        _sensorStepCount.value = 0
        initialSensorStepCount = null
    }

    private fun registerSensors() {
        registerSensor(Sensor.TYPE_STEP_COUNTER)
        registerSensor(Sensor.TYPE_HEART_RATE)
        registerSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> handleStepCounter(event)
            Sensor.TYPE_HEART_RATE -> handleHeartRate(event)
            Sensor.TYPE_ACCELEROMETER -> handleAccelerometer(event)
        }
    }

    private fun handleStepCounter(event: SensorEvent) {
        val currentSteps = event.values.firstOrNull() ?: return
        if (initialSensorStepCount == null) {
            initialSensorStepCount = currentSteps
        }
        _sensorStepCount.value = (currentSteps - initialSensorStepCount!!).toInt()
    }

    private fun handleHeartRate(event: SensorEvent) {
        val hr = event.values.firstOrNull()?.toInt() ?: return
        _heartRateFlow.tryEmit(hr)
    }

    private fun handleAccelerometer(event: SensorEvent) {
        val (x, y, z) = event.values
        _accelFlow.tryEmit(AccelData(x, y, z))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun registerSensor(type: Int) {
        val sensor = androidSensorManager.getDefaultSensor(type) ?: return
        androidSensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}

// Data classes
data class AccelData(val x: Float, val y: Float, val z: Float)