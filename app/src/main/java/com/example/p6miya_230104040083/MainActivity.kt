package id.antasari.p6miya_230104040083

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.antasari.p6miya_230104040083.data.*
import id.antasari.p6miya_230104040083.ui.*
import id.antasari.p6miya_230104040083.ui.theme.MindaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inisialisasi database dan repositori
        val db = MindaDatabase.getInstance(applicationContext)
        val diaryRepo = DiaryRepository(db.diaryDao())
        val prefsRepo = UserPrefsRepository(applicationContext)

        // ✅ Set UI dengan Jetpack Compose
        setContent {
            MindaTheme {
                val onboarded by prefsRepo.isOnboarded.collectAsState(initial = false)
                val navController = rememberNavController()
                val startDestination = if (onboarded) "home" else "onboarding"

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    // 🪄 Onboarding Screen
                    composable("onboarding") {
                        OnboardingScreens(
                            prefsRepo = prefsRepo,
                            onFinish = {
                                navController.navigate("home") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 🏠 Home Screen
                    composable("home") {
                        HomeScreen(
                            diaryRepo = diaryRepo,
                            prefsRepo = prefsRepo,
                            onOpenDetail = { id ->
                                navController.navigate("detail/$id")
                            },
                            onNewEntry = {
                                navController.navigate("new")
                            }
                        )
                    }

                    // 🧪 Optional Test Screen
                    composable("test") {
                        TestRoomScreen(repo = diaryRepo)
                    }

                    // ➕ New Entry
                    composable("new") {
                        NewEntryScreen(
                            diaryRepo = diaryRepo,
                            onCreated = { id ->
                                navController.navigate("detail/$id") {
                                    popUpTo("home")
                                }
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // 📄 Detail Screen
                    composable(
                        route = "detail/{entryId}",
                        arguments = listOf(
                            navArgument("entryId") {
                                type = NavType.LongType
                            }
                        )
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getLong("entryId") ?: 0L
                        NoteDetailScreen(
                            id = id,
                            diaryRepo = diaryRepo,
                            onBack = { navController.popBackStack() },
                            onEdit = { eid ->
                                navController.navigate("edit/$eid")
                            }
                        )
                    }

                    // ✏️ Edit Entry
                    composable(
                        route = "edit/{entryId}",
                        arguments = listOf(
                            navArgument("entryId") {
                                type = NavType.LongType
                            }
                        )
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getLong("entryId") ?: 0L
                        EditEntryScreen(
                            id = id,
                            diaryRepo = diaryRepo,
                            onSaved = { savedId ->
                                navController.navigate("detail/$savedId") {
                                    popUpTo("home")
                                }
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
