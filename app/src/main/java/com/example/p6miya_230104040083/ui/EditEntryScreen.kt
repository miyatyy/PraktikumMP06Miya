package id.antasari.p6miya_230104040083.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.antasari.p6miya_230104040083.data.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEntryScreen(
    id: Long,
    diaryRepo: DiaryRepository,
    onSaved: (Long) -> Unit,
    onCancel: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var entry by remember { mutableStateOf<DiaryEntry?>(null) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // Load entry data
    LaunchedEffect(id) {
        entry = diaryRepo.getById(id)
        entry?.let {
            title = it.title
            content = it.content
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Entry") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(onClick = {
                    scope.launch {
                        entry?.let {
                            diaryRepo.update(it.copy(title = title, content = content))
                            onSaved(it.id)
                        }
                    }
                }) {
                    Text("Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = onCancel) {
                    Text("Cancel")
                }
            }
        }
    }
}
