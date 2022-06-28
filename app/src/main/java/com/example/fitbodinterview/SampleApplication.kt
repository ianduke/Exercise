package com.example.fitbodinterview

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.fitbodinterview.data.Exercise
import com.example.fitbodinterview.data.ExerciseDao
import com.example.fitbodinterview.data.ExerciseRecord
import com.example.fitbodinterview.data.ExerciseRecordDao
import com.example.fitbodinterview.data.db.DataIngestWorker
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class SampleApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()

        val dataIngestWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<DataIngestWorker>().build()
        WorkManager
            .getInstance(this)
            .enqueue(dataIngestWorkRequest)
    }
}