package com.example.fitbodinterview.data.db.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise"
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @NonNull @ColumnInfo(name = "title") val title: String,
)