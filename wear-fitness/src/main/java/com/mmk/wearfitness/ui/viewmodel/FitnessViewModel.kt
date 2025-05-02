package com.mmk.wearfitness.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.wearfitness.domain.usecase.GetHeartRateUseCase
import com.mmk.wearfitness.domain.usecase.GetStepCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    getStepCountUseCase: GetStepCountUseCase,
    getHeartRateUseCase: GetHeartRateUseCase
) : ViewModel() {
    private val stepCount = getStepCountUseCase()
    private val heartRate = getHeartRateUseCase()

    val uiState: StateFlow<FitnessUiState> = combine(
        stepCount,
        heartRate
    ) { steps, hr ->
        FitnessUiState(
            steps = steps,
            heartRate = hr,
            lastUpdate = System.currentTimeMillis()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FitnessUiState()
    )
}

data class FitnessUiState(
    val steps: Int = 0,
    val heartRate: Int? = null,
    val lastUpdate: Long = 0L
)