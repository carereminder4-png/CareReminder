package com.carereminder.app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carereminder.app.domain.model.Task
import java.util.Locale.getDefault

@Composable
fun TaskCard(
    task: Task,
    onToggleComplete: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onEditClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Type: ${task.type}")

                Text(
                    text = "Time: ${
                        java.text.SimpleDateFormat(
                            "MMM dd, yyyy h:mm a", getDefault()
                        ).format(task.reminderTime)
                    }"
                )
            }

            Checkbox(
                checked = task.completed,
                onCheckedChange = {
                    onToggleComplete()
                }
            )
        }
    }
}