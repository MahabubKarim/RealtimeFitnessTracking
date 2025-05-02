package com.mmk.realtimefitnesstracking.sensor

import android.content.Context
import android.hardware.SensorManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.hardware.SensorManager as AndroidSensorManager


@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideAndroidSensorManager(
        @ApplicationContext context: Context
    ): AndroidSensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as AndroidSensorManager
    }
}
