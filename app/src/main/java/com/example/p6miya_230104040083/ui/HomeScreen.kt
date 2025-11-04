package id.antasari.p6miya_230104040083.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.antasari.p6miya_230104040083.data.*
import id.antasari.p6miya_230104040083.util.DateFormatter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    diaryRepo: DiaryRepository,
    prefsRepo: UserPrefsRepository,
    onOpenDetail: (Long) -> Unit,
    onNewEntry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val entries by diaryRepo.getAllFlow().collectAsState(initial = emptyList())
    val userName by prefsRepo.userName.collectAsState(initial = "Miya")
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hi, $userName 👋") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNewEntry,
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                text = { Text("New") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        if (entries.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("No entries yet. Tap 'New' to add one!")
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        scope.launch {
                            diaryRepo.add(
                                DiaryEntry(
                                    title = "Welcome, Miya!",
                                    content = "NIM 230104040083 — this is your first diary entry."
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text("Seed Sample Entry") }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(entries) { entry ->
                    DiaryListItem(entry = entry, onClick = { onOpenDetail(entry.id) })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun DiaryListItem(entry: DiaryEntry, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(entry.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            DateFormatter.format(entry.timestamp),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(entry.content, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
    }
}
