package com.example.todu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority: Int = 0, // 0: Low, 1: Medium, 2: High
    val tags: String? = null, // Comma-separated tags
    val isCompleted: Boolean = false,
    val taskType: String
)
