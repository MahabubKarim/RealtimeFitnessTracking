package com.mmk.wearfitness.data.datasource

import android.content.Context
import androidx.health.services.client.HealthServices
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.clearPassiveListenerCallback
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HealthServicesDataSource @Inject constructor(
    context: Context
) {
    private val healthServicesClient = HealthServices.getClient(context)
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient

    fun getHeartRate(): Flow<Int> = callbackFlow {
        val callback = object : PassiveListenerCallback {
            override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
                dataPoints.getData(DataType.HEART_RATE_BPM).forEach { dataPoint ->
                    trySend(dataPoint.value.toInt())
                }
            }
        }

        val config = PassiveListenerConfig.builder()
            .setDataTypes(setOf(DataType.HEART_RATE_BPM))
            .build()

        passiveMonitoringClient.setPassiveListenerCallback(config, Runnable::run, callback)

        awaitClose {
            launch {
                passiveMonitoringClient.clearPassiveListenerCallback()
            }
        }
    }

    fun getStepCount(): Flow<Int> = callbackFlow {
        val callback = object : PassiveListenerCallback {
            override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
                dataPoints.getData(DataType.STEPS).forEach { dataPoint ->
                    trySend(dataPoint.value.toInt())
                }
            }
        }

        val config = PassiveListenerConfig.builder()
            .setDataTypes(setOf(DataType.STEPS))
            .build()

        passiveMonitoringClient.setPassiveListenerCallback(config, Runnable::run, callback)

        awaitClose {
            launch {
                passiveMonitoringClient.clearPassiveListenerCallback()
            }
        }
    }
}