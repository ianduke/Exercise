package com.example.fitbodinterview.data.db

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fitbodinterview.R
import com.example.fitbodinterview.data.db.models.Exercise
import com.example.fitbodinterview.data.db.daos.ExerciseDao
import com.example.fitbodinterview.data.db.models.ExerciseRecord
import com.example.fitbodinterview.data.db.daos.ExerciseRecordDao
import com.example.fitbodinterview.utils.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class DataIngestWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val exerciseDao: ExerciseDao,
    private val exerciseRecordDao: ExerciseRecordDao,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private const val DATA_FILE_NAME = "workout_data.txt"
        private const val DATE_FORMAT = "MMM dd yyyy"
    }

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            exerciseRecordDao.deleteAll()
            exerciseDao.deleteAll()

            val inputStream = applicationContext.assets.open(DATA_FILE_NAME)
            inputStream.use { stream ->
                stream.reader(Charsets.UTF_8).use { reader ->
                    val exerciseList = context.resources.getStringArray(R.array.exercise_list)
                    exerciseList.forEach { exerciseName ->
                        exerciseDao.insert(Exercise(title = exerciseName))
                    }

                    reader.forEachLine { line ->
                        CoroutineScope(Dispatchers.IO).launch {
                            line.toExerciseRecord()?.let { exerciseRecord ->
                                exerciseRecordDao.insert(exerciseRecord)
                            }
                        }
                    }
                }
            }
        }
        return Result.success()
    }

    private suspend fun String.toExerciseRecord(): ExerciseRecord? {
        val data = this.split(",")
        if (data.size != 4) {
            Logger.w("XXXX: Invalid input data at line $this")
            return null
        }
        val date = data[0].toDate(DATE_FORMAT) ?: Date()
        val exerciseName = data[1]
        val reps = data[2].toInt()
        val weight = data[3].toInt()
        val oneRM = weight * 36 / (37 - reps)

        return ExerciseRecord(
            workoutDate = date,
            exerciseId = exerciseDao.getId(exerciseName) ?: 0,
            weight = weight,
            reps = reps,
            oneRM = oneRM
        )
    }

    private fun String.toDate(format: String): Date? {
        val dateFormat = SimpleDateFormat(format)
        return try {
            dateFormat.parse(this)
        } catch (e: ParseException) {
            Logger.w("XXXX: Error parsing date $this", e)
            null
        }
    }
}