package com.mmk.wearfitness.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.ambient.AmbientLifecycleObserver
import androidx.wear.ambient.AmbientLifecycleObserver.AmbientDetails
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.mmk.wearfitness.ui.theme.WearFitnessTheme
import com.mmk.wearfitness.services.SensorForegroundService
import com.mmk.wearfitness.ui.component.AmbientFitnessScreen
import com.mmk.wearfitness.ui.viewmodel.FitnessViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FitnessActivity : ComponentActivity(), AmbientLifecycleObserver.AmbientLifecycleCallback {
    private lateinit var ambientController: AmbientDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ambient mode
        ambientController = AmbientDetails(
            burnInProtectionRequired = true,
            deviceHasLowBitAmbient = true
        )
        setContent {
            WearFitnessTheme {
                FitnessApp()
            }
        }

        // Start foreground service
        SensorForegroundService.start(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SensorForegroundService.stop(this)
    }

    fun getAmbientCallback(): AmbientLifecycleObserver.AmbientLifecycleCallback = MyAmbientCallback()

    inner class MyAmbientCallback : AmbientLifecycleObserver.AmbientLifecycleCallback {
        override fun onEnterAmbient(ambientDetails: AmbientDetails) {
            // Handle entering ambient mode
        }

        override fun onExitAmbient() {
            // Handle exiting ambient mode
        }

        override fun onUpdateAmbient() {
            // Update your UI in ambient mode
        }
    }
}

@Composable
fun FitnessApp() {
    val navController = rememberSwipeDismissableNavController()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            // Clean up when the app is closed
            SensorForegroundService.stop(context)
        }
    }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = "fitness"
    ) {
        composable("fitness") {
            FitnessScreen()
        }
        // Add more destinations as needed
    }
}

/*
@Composable
fun FitnessScreenWithAmbient() {
    val viewModel: FitnessViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    AmbientLifecycleObserver.AmbientLifecycleCallback(
        ambientCallback = object : AmbientLifecycleObserver.AmbientLifecycleCallback {
            override fun onEnterAmbient(ambientDetails: AmbientDetails) {
                // Simplified UI for ambient mode
            }

            override fun onExitAmbient() {
                // Restore full UI
            }
        }
    ) { isAmbient ->
        if (isAmbient) {
            AmbientFitnessScreen(uiState)
        } else {
            FitnessScreen(viewModel)
        }
    }
}*/
