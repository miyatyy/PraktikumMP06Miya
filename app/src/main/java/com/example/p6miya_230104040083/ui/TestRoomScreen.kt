package id.antasari.p6miya_230104040083.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.antasari.p6miya_230104040083.data.DiaryRepository
import id.antasari.p6miya_230104040083.data.DiaryEntry
import kotlinx.coroutines.launch

@Composable
fun TestRoomScreen(repo: DiaryRepository, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Test Room Database")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                repo.add(
                    DiaryEntry(
                        title = "My Day 1",
                        content = "This is a sample entry by Miya (230104040083)."
                    )
                )
                repo.add(
                    DiaryEntry(
                        title = "Gratitude Journal",
                        content = "Feeling grateful today! — Miya"
                    )
                )
            }
        }) {
            Text("Add Dummy Entries")
        }
    }
}
