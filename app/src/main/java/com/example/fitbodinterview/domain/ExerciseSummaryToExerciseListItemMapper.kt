package com.example.fitbodinterview.domain

import com.example.fitbodinterview.data.db.models.ExerciseSummary
import com.example.fitbodinterview.ui.exerciselist.ExerciseListItem

class ExerciseSummaryToExerciseListItemMapper : Mapper<ExerciseSummary, ExerciseListItem>() {

    override fun from(from: ExerciseSummary): ExerciseListItem {
        return ExerciseListItem(
            exerciseId = from.exerciseId,
            exerciseName = from.exerciseName,
            oneRmRecord = from.oneRmRecord,
        )
    }
}