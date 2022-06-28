package com.example.fitbodinterview.data

import androidx.room.ColumnInfo

data class ExerciseSummary(
    @ColumnInfo(name = "exercise_id") val exerciseId: Long,
    @ColumnInfo(name = "exercise_name") val exerciseName: String,
    @ColumnInfo(name = "one_rm_record") val oneRmRecord: Int,
)