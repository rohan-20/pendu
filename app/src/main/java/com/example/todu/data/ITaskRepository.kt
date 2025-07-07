package com.example.todu.data

import kotlinx.coroutines.flow.Flow

interface ITaskRepository {
    fun getTasksByType(taskType: String): Flow<List<Task>>
    fun getTaskById(id: Int): Flow<Task?>
    suspend fun insert(task: Task)
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
}