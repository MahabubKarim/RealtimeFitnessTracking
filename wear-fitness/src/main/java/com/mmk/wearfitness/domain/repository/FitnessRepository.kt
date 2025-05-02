package com.mmk.wearfitness.domain.repository

import kotlinx.coroutines.flow.Flow

interface FitnessRepository {
    val stepCount: Flow<Int>
    val heartRate: Flow<Int?>
    suspend fun startTracking()
    suspend fun stopTracking()
}