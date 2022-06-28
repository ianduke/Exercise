package com.example.fitbodinterview.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRecordDao {

    @Query("SELECT id, workout_date, max(one_rm) as one_rm " +
            "FROM exercise_record " +
            "WHERE exercise_id = :exerciseId " +
            "GROUP BY workout_date " +
            "ORDER BY workout_date ASC")
    fun getExerciseHistory(exerciseId: Long): Flow<List<WorkoutData>>

    @Query("SELECT exercise.id as exercise_id, exercise.title as exercise_name, max(exercise_record.one_rm) as one_rm_record " +
            "FROM exercise, exercise_record " +
            "WHERE exercise.id = :exerciseId and exercise.id = exercise_record.exercise_id " +
            "GROUP BY exercise_name")
    fun getMaxOneRM(exerciseId: Long): Flow<ExerciseSummary>

    @Query("SELECT exercise.id as exercise_id, exercise.title as exercise_name, max(exercise_record.one_rm) as one_rm_record " +
            "FROM exercise, exercise_record " +
            "WHERE exercise.id = exercise_record.exercise_id " +
            "GROUP BY exercise_name")
    fun getMaxOneRMByExercise(): Flow<List<ExerciseSummary>>

    @Query("DELETE FROM exercise_record")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(exerciseRecord: ExerciseRecord)
}
