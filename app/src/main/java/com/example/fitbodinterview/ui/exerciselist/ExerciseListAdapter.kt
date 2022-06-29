package com.example.fitbodinterview.ui.exerciselist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbodinterview.databinding.ItemExerciseSummaryBinding

class ExerciseListAdapter(
    private val exerciseClickListener: OnExerciseClickListener,
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder>() {

    private val exercises: MutableList<ExerciseListItem> = mutableListOf()

    interface OnExerciseClickListener {
        fun onExerciseClicked(exerciseId: Long)
    }

    fun swapData(updatedExercises: List<ExerciseListItem>) {
        val diffResult = DiffUtil.calculateDiff(ExerciseListDiffCallback(this.exercises, updatedExercises))
        this.exercises.clear()
        this.exercises.addAll(updatedExercises)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        if (position in 0..exercises.size) {
            holder.bind(exercises[position])
        }
    }

    override fun getItemCount() = exercises.size

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseSummaryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseListItem) {
            with(binding) {
                exerciseName.text = exercise.exerciseName
                weight.text = exercise.oneRmRecord.toString()
                root.setOnClickListener {
                    exerciseClickListener.onExerciseClicked(exercise.exerciseId)
                }
            }
        }
    }
}