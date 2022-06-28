package com.example.fitbodinterview.ui.exerciselist

import androidx.lifecycle.ViewModel
import com.example.fitbodinterview.data.ExerciseRecordDao
import com.example.fitbodinterview.domain.ExerciseSummaryToExerciseListItemMapper
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val exerciseRecordDao: ExerciseRecordDao,
) : ViewModel() {

    fun getExercises(): Flow<List<ExerciseListItem>> = flow {
        exerciseRecordDao.getMaxOneRMByExercise()
            .distinctUntilChanged()
            .catch { Logger.w("Error get exercise summaries", it) }
            .collect { exerciseSummaries ->
                emit(
                    ExerciseSummaryToExerciseListItemMapper().from(exerciseSummaries)
                )
            }
    }
}