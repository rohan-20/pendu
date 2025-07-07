package com.example.todu.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) : ITaskRepository {
    override fun getTasksByType(taskType: String): Flow<List<Task>> = taskDao.getTasksByType(taskType)

    override fun getTaskById(id: Int): Flow<Task?> {
        return taskDao.getTaskById(id)
    }

    override suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    override suspend fun update(task: Task) {
        taskDao.update(task)
    }

    override suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}
