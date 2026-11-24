package com.carereminder.app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carereminder.app.domain.model.Task
import com.carereminder.app.domain.model.TaskType
import com.carereminder.app.ui.theme.CareReminderTheme

@Composable
fun HomeContent(
    state: HomeUiState,
    onAddTaskClick: () -> Unit,
    onToggleComplete: (Task) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Text("+")
            }
        }
    ) { padding ->

        when {

            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.tasks.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No reminders added yet")
                }
            }

            else -> {

                val todayTasks = state.tasks.filter { !it.completed }

                val upcomingTasks = state.tasks.filter { it.completed }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {
                        Text(
                            text = "Today's Tasks",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    items(todayTasks) { task ->
                        TaskCard(
                            task = task,
                            onToggleComplete = {
                                onToggleComplete(task)
                            }
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Upcoming Reminders",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    items(upcomingTasks) { task ->
                        TaskCard(
                            task = task,
                            onToggleComplete = {
                                onToggleComplete(task)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreviewLight() {

    CareReminderTheme(
        darkTheme = false
    ) {

        HomeContent(
            state = HomeUiState(
                isLoading = false,
                tasks = listOf(
                    Task(
                        title = "Take Morning Medicine",
                        type = TaskType.MEDICINE,
                        reminderTime = System.currentTimeMillis(),
                        completed = false
                    ),
                    Task(
                        title = "Drink Water",
                        type = TaskType.OTHER,
                        reminderTime = System.currentTimeMillis(),
                        completed = true
                    )
                )
            ),
            onAddTaskClick = {},
            onToggleComplete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreviewDark() {

    CareReminderTheme(
        darkTheme = true
    ) {

        HomeContent(
            state = HomeUiState(
                isLoading = false,
                tasks = listOf(
                    Task(
                        title = "Take Morning Medicine",
                        type = TaskType.MEDICINE,
                        reminderTime = System.currentTimeMillis(),
                        completed = false
                    ),
                    Task(
                        title = "Drink Water",
                        type = TaskType.OTHER,
                        reminderTime = System.currentTimeMillis(),
                        completed = true
                    )
                )
            ),
            onAddTaskClick = {},
            onToggleComplete = {}
        )
    }
}