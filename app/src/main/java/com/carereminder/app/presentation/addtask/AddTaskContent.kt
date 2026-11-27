package com.carereminder.app.presentation.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carereminder.app.domain.model.TaskType
import com.carereminder.app.ui.theme.CareReminderTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTaskContent(
    state: AddTaskUiState,
    isEditMode: Boolean,
    onTitleChange: (String) -> Unit,
    onTaskTypeChange: (TaskType) -> Unit,
    onReminderTimeChange: (Long) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (state.reminderTime != 0L) {
        calendar.timeInMillis = state.reminderTime
    }

    val dateFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())
    val dateText = if (state.reminderTime == 0L) "Not set" else dateFormat.format(calendar.time)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            onReminderTimeChange(calendar.timeInMillis)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = if (isEditMode) {
                    "Edit Reminder"
                } else {
                    "Add Reminder"
                },
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

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = state.taskType == type,
                        onClick = {
                            onTaskTypeChange(type)
                        }
                    )

                    Text(
                        text = type.name
                    )
                }
            }

            OutlinedTextField(
                value = dateText,
                onValueChange = {},
                label = {
                    Text("Reminder Date & Time")
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        datePickerDialog.show()
                    },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pick Date and Time"
                    )
                }
            )

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isEditMode) {
                        "Update Task"
                    } else {
                        "Save Task"
                    }
                )
            }

            if (isEditMode) {

                OutlinedButton(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete Task")
                }
            }
        }
    }

    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("Delete Task")
            },
            text = {
                Text(
                    "Are you sure you want to delete this reminder?"
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
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
            isEditMode = false,
            onTitleChange = {},
            onTaskTypeChange = {},
            onReminderTimeChange = {},
            onSave = {},
            onDelete = {}
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
            isEditMode = true,
            onTitleChange = {},
            onTaskTypeChange = {},
            onReminderTimeChange = {},
            onSave = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskEmptyPreview() {

    CareReminderTheme {

        AddTaskContent(
            state = AddTaskUiState(),
            isEditMode = false,
            onTitleChange = {},
            onTaskTypeChange = {},
            onReminderTimeChange = {},
            onSave = {},
            onDelete = {}
        )
    }
}