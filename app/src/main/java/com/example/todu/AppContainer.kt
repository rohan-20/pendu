package com.example.todu

import android.content.Context
import com.example.todu.data.TaskDatabase
import com.example.todu.data.ITaskRepository
import com.example.todu.data.TaskRepository

interface AppContainer {
    val taskRepository: ITaskRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val taskRepository: ITaskRepository = TaskRepository(TaskDatabase.getDatabase(context).taskDao())
}
