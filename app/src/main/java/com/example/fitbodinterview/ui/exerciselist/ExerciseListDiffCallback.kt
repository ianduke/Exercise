package com.example.fitbodinterview.ui.exerciselist

import androidx.recyclerview.widget.DiffUtil
import com.example.fitbodinterview.data.ExerciseSummary

class ExerciseListDiffCallback(
    private val oldList: List<ExerciseListItem>,
    private val newList: List<ExerciseListItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].exerciseId == newList[newItemPosition].exerciseId


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.oneRmRecord == newItem.oneRmRecord && oldItem.exerciseName == newItem.exerciseName
    }
}