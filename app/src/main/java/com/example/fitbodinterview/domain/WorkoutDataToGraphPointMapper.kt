package com.example.fitbodinterview.domain

import com.example.fitbodinterview.data.WorkoutData
import com.example.fitbodinterview.ui.graph.GraphPoint

class WorkoutDataToGraphPointMapper : Mapper<WorkoutData, GraphPoint>() {

    companion object {
        private const val ONE_DAY_IN_MILLIS = 60 * 60 * 24 * 1000
    }

    override fun from(from: WorkoutData): GraphPoint {
        val workoutDateInDays = from.workoutDate / ONE_DAY_IN_MILLIS
        return GraphPoint(
            x = workoutDateInDays.toInt(),
            y = from.oneRM
        )
    }
}