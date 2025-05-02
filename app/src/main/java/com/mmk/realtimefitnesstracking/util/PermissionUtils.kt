package com.mmk.realtimefitnesstracking.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

/**
 * Utility object to check and request runtime permissions.
 */
object PermissionUtils {
    /**
     * Returns true if both fine and coarse location permissions are granted.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasLocationPermissions(context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED

    /**
     * Registers a launcher on the given activity to request location permissions.
     * Call this in Activity.onCreate before setContent.
     * Example:
     * val permissionLauncher = PermissionUtils.registerLocationPermissionLauncher(this) { granted ->
     *     if (granted) viewModel.startTracking() else /* show rationale or disable features */
     * }
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun registerLocationPermissionLauncher(
        activity: ComponentActivity,
        onResult: (granted: Boolean) -> Unit
    ) = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Both must be granted
        val fine = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarse = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val activityRecognition = permissions[Manifest.permission.ACTIVITY_RECOGNITION] ?: false
        val bodySensors = permissions[Manifest.permission.BODY_SENSORS] ?: false
        onResult(fine && coarse && activityRecognition && bodySensors)
    }
}