package com.example.todu.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todu.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    viewModel: TaskViewModel,
    taskId: Int,
    onBack: () -> Unit
) {
    val task = viewModel.getTaskById(taskId).collectAsState(initial = null).value

    var title by remember(task) { mutableStateOf(task?.title ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Pendu") },
                
                actions = {
                    TextButton(onClick = {
                        task?.let {
                            viewModel.update(
                                it.copy(
                                    title = title
                                )
                            )
                            onBack()
                        }
                    }) {
                        Text("Update Pendu")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Pendu Title") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}