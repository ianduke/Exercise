package com.example.fitbodinterview.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitbodinterview.data.db.models.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT title FROM exercise WHERE id=:exerciseId")
    suspend fun getTitle(exerciseId: Long): String?

    @Query("SELECT id FROM exercise WHERE title=:exerciseTitle")
    suspend fun getId(exerciseTitle: String): Long?

    @Query("DELETE FROM exercise")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(exercise: Exercise): Long
}
