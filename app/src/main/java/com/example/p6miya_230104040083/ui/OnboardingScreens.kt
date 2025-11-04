package id.antasari.p6miya_230104040083.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.antasari.p6miya_230104040083.data.UserPrefsRepository
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreens(prefsRepo: UserPrefsRepository, onFinish: () -> Unit) {
    var step by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("Miya") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when (step) {
            1 -> {
                Text("Welcome to Minda — Your mind, in one place.")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { step = 2 }) { Text("Next") }
            }
            2 -> {
                Text("What's your name?")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Your name") })
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { step = 3 }) { Text("Next") }
            }
            3 -> {
                Text("Welcome to your Minda, $name 💫")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    scope.launch {
                        prefsRepo.setUserName(name)
                        prefsRepo.setOnboarded(true)
                        onFinish()
                    }
                }) { Text("Open Minda") }
            }
        }
    }
}
