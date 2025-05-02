package com.mmk.realtimefitnesstracking.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val distanceMeters: Float,
    val speedMetersPerSecond: Float,
    val caloriesBurned: Float,
    val timestamp: Long
)
