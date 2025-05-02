package com.mmk.realtimefitnesstracking.data.remote

import com.mmk.realtimefitnesstracking.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

// Simulated remote data source (stub API)
class RemoteDataSource {

    // Simulate fetching workouts from remote API
    fun fetchWorkouts(): Flow<List<Workout>> = flow {
        // Simulated delay
        kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(1))
        // Emit empty list or sample data
        emit(emptyList())
    }
}
