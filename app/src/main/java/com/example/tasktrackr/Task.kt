package com.example.tasktrackr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val hour: Int,
    val minute: Int

)