package com.mmk.wearfitness.data.datasource

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SensorDataSource @Inject constructor(
    private val context: Context
) : SensorEventListener {
    private val _stepCount = MutableStateFlow(0)
    private val _heartRate = MutableStateFlow<Int?>(null)

    private lateinit var sensorManager: SensorManager
    private var initialSteps: Float? = null

    val stepCount: StateFlow<Int> = _stepCount
    val heartRate: StateFlow<Int?> = _heartRate

    fun start() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
        initialSteps = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                if (initialSteps == null) {
                    initialSteps = event.values[0]
                }
                _stepCount.value = (event.values[0] - initialSteps!!).toInt()
            }
            Sensor.TYPE_HEART_RATE -> {
                _heartRate.value = event.values[0].toInt()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}