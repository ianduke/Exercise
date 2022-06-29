package com.example.fitbodinterview.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitbodinterview.data.db.models.Exercise
import com.example.fitbodinterview.data.db.daos.ExerciseDao
import com.example.fitbodinterview.data.db.models.ExerciseRecord
import com.example.fitbodinterview.data.db.daos.ExerciseRecordDao

@Database(
    entities = [
        Exercise::class,
        ExerciseRecord::class,
    ],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseDetailDao(): ExerciseRecordDao
}
