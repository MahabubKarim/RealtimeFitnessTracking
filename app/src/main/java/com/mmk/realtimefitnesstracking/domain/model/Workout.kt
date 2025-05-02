package com.mmk.realtimefitnesstracking.domain.model

data class Workout(
    val distanceMeters: Float,
    val speedMetersPerSecond: Float,
    val caloriesBurned: Float,
    val timestamp: Long
)
