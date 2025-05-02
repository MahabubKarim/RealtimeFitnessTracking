package com.mmk.wearfitness.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Text
import com.mmk.wearfitness.ui.viewmodel.FitnessUiState

@Composable
fun AmbientFitnessScreen(
    uiState: FitnessUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Simplified ambient display - white text on black background
        Text(
            text = "Steps: ${uiState.steps}",
            color = Color.White
        )
        Text(
            text = "HR: ${uiState.heartRate ?: "--"}",
            color = Color.White
        )
    }
}