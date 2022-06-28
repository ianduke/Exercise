package com.example.fitbodinterview.data

import androidx.room.ColumnInfo

class WorkoutData(
    @ColumnInfo(name = "workout_date") val workoutDate: Long,
    @ColumnInfo(name = "one_rm") val oneRM: Int,
)