package com.mmk.wearfitness.data.repository

import com.mmk.wearfitness.data.datasource.HealthServicesDataSource
import com.mmk.wearfitness.data.datasource.SensorDataSource
import com.mmk.wearfitness.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FitnessRepositoryImpl @Inject constructor(
    private val sensorDataSource: SensorDataSource,
    private val healthServicesDataSource: HealthServicesDataSource
) : FitnessRepository {
    override val stepCount: Flow<Int> = healthServicesDataSource.getStepCount()
        .catch { emitAll(sensorDataSource.stepCount) }

    override val heartRate: Flow<Int?> = healthServicesDataSource.getHeartRate()
        //.catch { emitAll(sensorDataSource.heartRate) }
        .map { it as Int? }

    override suspend fun startTracking() {
        sensorDataSource.start()
    }

    override suspend fun stopTracking() {
        sensorDataSource.stop()
    }
}