package com.example.fitbodinterview.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "exercise_record",
    foreignKeys = [ForeignKey(
        entity = Exercise::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("exercise_id")
    )],
)
data class ExerciseRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @NonNull @ColumnInfo(name = "workout_date") val workoutDate: Date,
    @NonNull @ColumnInfo(name = "exercise_id", index = true) val exerciseId: Long,
    @NonNull @ColumnInfo(name = "weight") val weight: Int,
    @NonNull @ColumnInfo(name = "reps") val reps: Int,
    @NonNull @ColumnInfo(name = "one_rm") val oneRM: Int,
)