package com.example.todu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todu.data.Task
import com.example.todu.data.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import com.example.todu.data.ITaskRepository

class TaskViewModel(private val repository: ITaskRepository) : ViewModel() {

    val allTasks: Flow<List<Task>> = repository.allTasks

    fun getTaskById(id: Int): Flow<Task?> {
        return repository.getTaskById(id)
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}

class TaskViewModelFactory(private val repository: ITaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
