package com.shealt.healthreport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shealt.healthreport.ui.viewmodels.MainViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    var startDate by remember { mutableStateOf(LocalDate.now().minusDays(7)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    
    val isGenerating by viewModel.isGeneratingReport.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val exportedFile by viewModel.exportedFile.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(exportedFile) {
        exportedFile?.let { file ->
            try {
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "application/json"
                    putExtra(android.content.Intent.EXTRA_STREAM, uri)
                    flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                val chooser = android.content.Intent.createChooser(intent, "JSON Verisini Paylaş")
                context.startActivity(chooser)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                viewModel.clearExportedFile()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Veri Dışa Aktar") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tarih Aralığı Seçin", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Simple date selection (for a real app, use DatePickerDialog)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Başlangıç", style = MaterialTheme.typography.labelMedium)
                            Button(onClick = { startDate = startDate.minusDays(1) }) {
                                Text(startDate.toString())
                            }
                        }
                        Text("-")
                        Column {
                            Text("Bitiş", style = MaterialTheme.typography.labelMedium)
                            Button(onClick = { endDate = endDate.minusDays(1) }) {
                                Text(endDate.toString())
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.exportDateRange(startDate, endDate) },
                enabled = !isGenerating,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Dışa Aktarılıyor...")
                } else {
                    Text("Seçili Aralığı JSON Olarak Çıkar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            statusMessage?.let { msg ->
                Text(msg, color = MaterialTheme.colorScheme.primary)
            }
            errorMessage?.let { msg ->
                Text(msg, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
