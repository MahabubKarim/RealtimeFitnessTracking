package com.mmk.wearfitness.di

import android.content.Context
import com.mmk.wearfitness.data.datasource.HealthServicesDataSource
import com.mmk.wearfitness.data.datasource.SensorDataSource
import com.mmk.wearfitness.data.repository.FitnessRepositoryImpl
import com.mmk.wearfitness.domain.repository.FitnessRepository
import com.mmk.wearfitness.domain.usecase.GetHeartRateUseCase
import com.mmk.wearfitness.domain.usecase.GetStepCountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FitnessModule {
    @Provides
    @Singleton
    fun provideSensorDataSource(@ApplicationContext context: Context): SensorDataSource {
        return SensorDataSource(context)
    }

    @Provides
    @Singleton
    fun provideHealthServicesDataSource(@ApplicationContext context: Context): HealthServicesDataSource {
        return HealthServicesDataSource(context)
    }

    @Provides
    @Singleton
    fun provideFitnessRepository(
        sensorDataSource: SensorDataSource,
        healthServicesDataSource: HealthServicesDataSource
    ): FitnessRepository {
        return FitnessRepositoryImpl(sensorDataSource, healthServicesDataSource)
    }

    @Provides
    fun provideGetStepCountUseCase(repository: FitnessRepository): GetStepCountUseCase {
        return GetStepCountUseCase(repository)
    }

    @Provides
    fun provideGetHeartRateUseCase(repository: FitnessRepository): GetHeartRateUseCase {
        return GetHeartRateUseCase(repository)
    }
}