package com.mmk.realtimefitnesstracking

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
//import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.mmk.realtimefitnesstracking.ui.screen.FitnessScreen
import com.mmk.realtimefitnesstracking.ui.theme.RealtimeFitnessTrackingTheme
import com.mmk.realtimefitnesstracking.ui.viewmodels.FitnessViewModel
import com.mmk.realtimefitnesstracking.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val fitnessViewModel: FitnessViewModel by viewModels()
    //private lateinit var fitnessViewModel: FitnessViewModel
    /*private var fitnessViewModel: FitnessViewModel = ViewModelProvider(
        this,
        FitnessViewModelFactory(sensorManager)
    )[FitnessViewModel::class.java]*/
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Manually create ViewModel
       // fitnessViewModel = ViewModelProvider(this)[FitnessViewModel::class.java]
       /* fitnessViewModel = ViewModelProvider(
            this,
            FitnessViewModelFactory(sensorManager)
        )[FitnessViewModel::class.java]*/


        // Initialize permission launcher
        locationPermissionLauncher = PermissionUtils.registerLocationPermissionLauncher(this) { granted ->
            if (granted) fitnessViewModel.startTracking()
            else {
                // Show rationale or disable tracking features
            }
        }

        //enableEdgeToEdge()
        setContent {
            RealtimeFitnessTrackingTheme {
                Surface {
                    FitnessScreen(viewModel = fitnessViewModel)
                }
            }
        }

        // Request location permission if not already granted
        if (!PermissionUtils.hasLocationPermissions(this)) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.BODY_SENSORS
                )
            )
        } else {
            fitnessViewModel.startTracking()
        }
    }

    override fun onStart() {
        super.onStart()
        fitnessViewModel.startTracking() // start tracking when activity becomes visible
    }

    override fun onStop() {
        super.onStop()
        fitnessViewModel.stopTracking() // stop tracking when activity is no longer visible
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RealtimeFitnessTrackingTheme {
        FitnessScreen(null)
    }
}