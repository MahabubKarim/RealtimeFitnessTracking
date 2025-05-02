package com.mmk.realtimefitnesstracking.data.repository

import com.mmk.realtimefitnesstracking.data.local.WorkoutDao
import com.mmk.realtimefitnesstracking.data.local.WorkoutEntity
import com.mmk.realtimefitnesstracking.data.remote.RemoteDataSource
import com.mmk.realtimefitnesstracking.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val remoteDataSource: RemoteDataSource
) {
    // Emits combined flow of local and remote workouts
    val workouts: Flow<List<Workout>> = combine(
        workoutDao.getAllWorkouts(),
        remoteDataSource.fetchWorkouts()
    ) { localWorkouts, remoteWorkouts ->
        val localMapped = localWorkouts.map { it.toDomain() }
        // For now, just combine local and remote lists
        localMapped + remoteWorkouts
    }

    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout.toEntity())
    }

    private fun WorkoutEntity.toDomain(): Workout {
        return Workout(
            distanceMeters = this.distanceMeters,
            speedMetersPerSecond = this.speedMetersPerSecond,
            caloriesBurned = this.caloriesBurned,
            timestamp = this.timestamp
        )
    }

    private fun Workout.toEntity(): WorkoutEntity {
        return WorkoutEntity(
            distanceMeters = this.distanceMeters,
            speedMetersPerSecond = this.speedMetersPerSecond,
            caloriesBurned = this.caloriesBurned,
            timestamp = this.timestamp
        )
    }
}
