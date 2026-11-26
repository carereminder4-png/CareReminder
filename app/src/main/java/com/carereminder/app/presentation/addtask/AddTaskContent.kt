package com.carereminder.app.presentation.addtask

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carereminder.app.domain.model.TaskType
import com.carereminder.app.ui.theme.CareReminderTheme

@Composable
fun AddTaskContent(
    state: AddTaskUiState,
    onTitleChange: (String) -> Unit,
    onTaskTypeChange: (TaskType) -> Unit,
    onSave: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Add Reminder",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = state.title,
            onValueChange = onTitleChange,
            label = {
                Text("Task Title")
            },
            modifier = Modifier.fillMaxWidth()
        )

        TaskType.entries.forEach { type ->

            Row {

                RadioButton(
                    selected = state.taskType == type,
                    onClick = {
                        onTaskTypeChange(type)
                    }
                )

                Text(type.name)
            }
        }

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Task")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskPreviewLight() {

    CareReminderTheme(
        darkTheme = false
    ) {

        AddTaskContent(
            state = AddTaskUiState(
                title = "Take Morning Medicine",
                taskType = TaskType.MEDICINE
            ),
            onTitleChange = {},
            onTaskTypeChange = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskPreviewDark() {

    CareReminderTheme(
        darkTheme = true
    ) {

        AddTaskContent(
            state = AddTaskUiState(
                title = "Take Morning Medicine",
                taskType = TaskType.MEDICINE
            ),
            onTitleChange = {},
            onTaskTypeChange = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskEmptyPreview() {

    CareReminderTheme {

        AddTaskContent(
            state = AddTaskUiState(),
            onTitleChange = {},
            onTaskTypeChange = {},
            onSave = {}
        )
    }
}