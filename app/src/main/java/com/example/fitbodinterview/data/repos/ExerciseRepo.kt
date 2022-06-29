package com.example.fitbodinterview.data.repos

import com.example.fitbodinterview.data.db.daos.ExerciseDao
import com.example.fitbodinterview.data.db.models.Exercise
import javax.inject.Inject

class ExerciseRepo @Inject constructor(private val exerciseDao: ExerciseDao) {

    suspend fun getTitle(exerciseId: Long): String? =
        exerciseDao.getTitle(exerciseId)

    suspend fun getId(exerciseTitle: String): Long? =
        exerciseDao.getId(exerciseTitle)

    suspend fun deleteAll() =
        exerciseDao.deleteAll()

    suspend fun addExercise(exercise: Exercise): Long =
        exerciseDao.insert(exercise)
}