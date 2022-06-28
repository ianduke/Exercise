package com.example.fitbodinterview.ui.exercisedetail

import androidx.lifecycle.ViewModel
import com.example.fitbodinterview.data.ExerciseSummary
import com.example.fitbodinterview.data.ExerciseRecordDao
import com.example.fitbodinterview.domain.WorkoutDataToGraphPointMapper
import com.example.fitbodinterview.ui.graph.GraphPoint
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val exerciseRecordDao: ExerciseRecordDao,
) : ViewModel() {

    fun getExerciseListItem(exerciseId: Long): Flow<ExerciseSummary> = flow {
        exerciseRecordDao.getMaxOneRM(exerciseId)
            .distinctUntilChanged()
            .catch { Logger.w("XXXX: Error getting exercise list item", it) }
            .collect { exerciseListItem ->
                emit(exerciseListItem)
            }
    }

    fun graphPoints(exerciseId: Long): Flow<List<GraphPoint>> = flow {
        exerciseRecordDao.getExerciseHistory(exerciseId)
            .catch {  }
            .collect { workoutData ->
                emit(
                    WorkoutDataToGraphPointMapper().from(workoutData)
                )
            }
    }
}