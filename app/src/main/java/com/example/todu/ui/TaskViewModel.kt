package com.example.todu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todu.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

import com.example.todu.data.ITaskRepository

class TaskViewModel(private val repository: ITaskRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val allTasks: Flow<List<Task>> = combine(
        repository.allTasks,
        _searchQuery
    ) { tasks, searchQuery ->
        tasks
            .filter { task ->
                task.title.contains(searchQuery, ignoreCase = true) ||
                        (task.description?.contains(searchQuery, ignoreCase = true) ?: false)
            }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

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
