package com.mmk.realtimefitnesstracking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmk.realtimefitnesstracking.data.local.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}
