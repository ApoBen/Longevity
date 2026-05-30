package com.shealt.healthreport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shealt.healthreport.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: MainViewModel) {
    val autoReportEnabled by viewModel.autoReportEnabled.collectAsState()
    val reportHour by viewModel.reportHour.collectAsState()
    val reportMinute by viewModel.reportMinute.collectAsState()

    var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ayarlar",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Otomatik Rapor Oluşturma",
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = autoReportEnabled,
                onCheckedChange = { viewModel.setAutoReportEnabled(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (autoReportEnabled) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Oluşturma Saati: ${String.format("%02d:%02d", reportHour, reportMinute)}",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = { showTimePicker = true }) {
                    Text("Değiştir")
                }
            }
        }

        if (showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = reportHour,
                initialMinute = reportMinute,
                is24Hour = true
            )
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.setReportTime(timePickerState.hour, timePickerState.minute)
                        showTimePicker = false
                    }) {
                        Text("Tamam")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text("İptal")
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}
