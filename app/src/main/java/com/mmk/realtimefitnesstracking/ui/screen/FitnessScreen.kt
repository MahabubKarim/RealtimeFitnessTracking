package com.mmk.realtimefitnesstracking.ui.screen

import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mmk.realtimefitnesstracking.sensor.FitnessSensorManager
import com.mmk.realtimefitnesstracking.ui.viewmodels.FitnessViewModel

@Composable
fun FitnessScreen(viewModel: FitnessViewModel?) {
    val steps by viewModel!!.steps.collectAsState()
    val heartRate by viewModel.heartRate.collectAsState(initial = 0)
    val speed by viewModel.walkingSpeed.collectAsState()
    val stepFrequency by viewModel.stepFrequency.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D0D3F), Color(0xFF1F1F7A))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // Glowing Step Counter
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, Color.Transparent),
                        radius = 300f
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${steps}",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Steps",
                    fontSize = 20.sp,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Section
        Text(
            text = "Heart Rate: ${heartRate ?: "--"} bpm",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Speed: ${String.format("%.1f", speed)} mph",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Step Frequency: $stepFrequency spm",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun FitnessScreenPreview() {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val androidSensorManager = context.getSystemService(SensorManager::class.java)
    val sensorManager = FitnessSensorManager(context, androidSensorManager)
    val viewModel = FitnessViewModel(sensorManager)

    FitnessScreen(viewModel)
}