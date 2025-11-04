package id.antasari.p6miya_230104040083.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🌿 Palet hijau muda lembut
private val LightColors = lightColorScheme(
    primary = Color(0xFF8BC34A),
    onPrimary = Color.White,
    secondary = Color(0xFFDCEDC8),
    onSecondary = Color(0xFF1B5E20),
    background = Color(0xFFF1F8E9),
    onBackground = Color(0xFF1B5E20),
    surface = Color.White,
    onSurface = Color(0xFF1B5E20)
)

// 🌱 Tema malam hijau gelap
private val DarkColors = darkColorScheme(
    primary = Color(0xFF558B2F),
    onPrimary = Color.White,
    secondary = Color(0xFF33691E),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0)
)

@Composable
fun MindaTheme(
    darkTheme: Boolean = false, // ⚡ paksa terang dulu
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
