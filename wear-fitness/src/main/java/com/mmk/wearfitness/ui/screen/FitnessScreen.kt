package com.mmk.wearfitness.ui.screen

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mmk.wearfitness.ui.component.MetricTile
import com.mmk.wearfitness.ui.viewmodel.FitnessViewModel
import com.mmk.wearfitness.services.SensorForegroundService

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FitnessScreen(
    viewModel: FitnessViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScalingLazyListState()

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.ACTIVITY_RECOGNITION
        )
    } else {
        listOf(Manifest.permission.BODY_SENSORS)
    }

    val permissionState = rememberMultiplePermissionsState(permissions)

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
        SensorForegroundService.start(context)
    }

    ScalingLazyColumn(
        modifier = modifier.fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            MetricTile(
                value = uiState.steps.toString(),
                label = "Steps",
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            MetricTile(
                value = uiState.heartRate?.toString() ?: "--",
                label = "Heart Rate",
                modifier = Modifier.padding(8.dp)
            )
        }
        // Add more items or navigation targets as needed
    }
}