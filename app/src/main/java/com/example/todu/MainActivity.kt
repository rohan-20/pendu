package com.example.todu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todu.ui.*
import com.example.todu.ui.theme.ToduTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = AppDataContainer(applicationContext)

        setContent {
            ToduTheme {
                val navController = rememberNavController()
                val taskViewModel: TaskViewModel =
                    viewModel(factory = TaskViewModelFactory(appContainer.taskRepository))

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val edgeSwipeThreshold = with(LocalDensity.current) { 50.dp.toPx() }

                    BackHandler {
                        if (navController.currentBackStackEntry?.destination?.route != "tasks_list") {
                            navController.popBackStack()
                        } else {
                            finish()
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consumePositionChange()
                                    if (dragAmount.x > 0 && change.position.x < edgeSwipeThreshold) {
                                        if (navController.currentBackStackEntry?.destination?.route != "tasks_list") {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            }
                    ) {
                        NavHost(navController = navController, startDestination = "tasks_list") {
                            composable("tasks_list") {
                                TaskScreen(
                                    viewModel = taskViewModel,
                                    onAddTaskClick = { navController.navigate("add_task") },
                                    onTaskClick = { taskId -> navController.navigate("edit_task/$taskId") }
                                )
                            }
                            composable("add_task") {
                                AddTaskScreen(taskViewModel) {
                                    navController.popBackStack()
                                }
                            }
                            composable(
                                route = "edit_task/{taskId}",
                                arguments = listOf(navArgument("taskId") {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val taskId = backStackEntry.arguments?.getInt("taskId")
                                if (taskId != null) {
                                    EditTaskScreen(taskViewModel, taskId) {
                                        navController.popBackStack()
                                    }
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToduTheme {
        // Preview UI goes here
    }
}
