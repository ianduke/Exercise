package com.example.fitbodinterview.data.repos

import com.example.fitbodinterview.data.db.daos.ExerciseRecordDao
import com.example.fitbodinterview.data.db.models.ExerciseRecord
import com.example.fitbodinterview.data.db.models.ExerciseSummary
import com.example.fitbodinterview.data.db.models.WorkoutData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRecordRepo @Inject constructor(
    private val exerciseRecordDao: ExerciseRecordDao
) {

    fun getExerciseHistoryRepo(exerciseId: Long): Flow<List<WorkoutData>> =
        exerciseRecordDao.getExerciseHistoryByDay(exerciseId)

    fun getMaxOneRM(exerciseId: Long): Flow<ExerciseSummary> =
        exerciseRecordDao.getMaxOneRM(exerciseId)

    fun getMaxOneRMByExercise(): Flow<List<ExerciseSummary>> =
        exerciseRecordDao.getMaxOneRMByExercise()

    suspend fun addRecord(exerciseRecord: ExerciseRecord) =
        exerciseRecordDao.insert(exerciseRecord)
}