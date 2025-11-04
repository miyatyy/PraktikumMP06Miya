package id.antasari.p6miya_230104040083.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.antasari.p6miya_230104040083.data.*
import id.antasari.p6miya_230104040083.util.DateFormatter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    id: Long,
    diaryRepo: DiaryRepository,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val scope = rememberCoroutineScope()
    var entry by remember { mutableStateOf<DiaryEntry?>(null) }

    // Ambil data diary berdasarkan ID
    LaunchedEffect(id) { entry = diaryRepo.getById(id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(entry?.title ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(id) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        scope.launch {
                            entry?.let {
                                diaryRepo.remove(it)
                                onBack()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { padding ->
        entry?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    DateFormatter.format(it.timestamp),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(it.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                Text(it.content, style = MaterialTheme.typography.bodyLarge)
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    }
}
