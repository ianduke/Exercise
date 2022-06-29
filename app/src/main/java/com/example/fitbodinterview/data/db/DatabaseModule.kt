package com.example.fitbodinterview.data.db

import android.content.Context
import androidx.room.Room
import com.example.fitbodinterview.data.db.daos.ExerciseDao
import com.example.fitbodinterview.data.db.daos.ExerciseRecordDao
import com.example.fitbodinterview.data.repos.ExerciseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao()
    }

    @Provides
    fun provideExerciseRecordDao(appDatabase: AppDatabase): ExerciseRecordDao {
        return appDatabase.exerciseDetailDao()
    }

    companion object {
        private const val DATABASE_NAME = "database"
    }
}