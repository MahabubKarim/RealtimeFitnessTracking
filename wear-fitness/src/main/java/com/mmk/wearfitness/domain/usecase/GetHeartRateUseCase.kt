package com.mmk.wearfitness.domain.usecase

import com.mmk.wearfitness.domain.repository.FitnessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeartRateUseCase @Inject constructor(
    private val repository: FitnessRepository
) {
    operator fun invoke(): Flow<Int?> = repository.heartRate
}