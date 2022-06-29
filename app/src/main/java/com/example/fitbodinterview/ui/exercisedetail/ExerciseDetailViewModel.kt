package com.example.fitbodinterview.ui.exercisedetail

import androidx.lifecycle.ViewModel
import com.example.fitbodinterview.data.db.models.ExerciseSummary
import com.example.fitbodinterview.data.db.daos.ExerciseRecordDao
import com.example.fitbodinterview.data.repos.ExerciseRecordRepo
import com.example.fitbodinterview.domain.WorkoutDataToGraphPointMapper
import com.example.fitbodinterview.ui.graph.GraphPoint
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val exerciseRecordRepo: ExerciseRecordRepo,
) : ViewModel() {

    fun getExerciseListItem(exerciseId: Long): Flow<ExerciseSummary> = flow {
        exerciseRecordRepo.getMaxOneRM(exerciseId)
            .distinctUntilChanged()
            .catch { Logger.w("XXXX: Error getting exercise list item", it) }
            .collect { exerciseListItem ->
                emit(exerciseListItem)
            }
    }

    fun graphPoints(exerciseId: Long): Flow<List<GraphPoint>> = flow {
        exerciseRecordRepo.getExerciseHistory(exerciseId)
            .catch {  }
            .collect { workoutData ->
                emit(
                    WorkoutDataToGraphPointMapper().from(workoutData)
                )
            }
    }
}