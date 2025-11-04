package id.antasari.p6miya_230104040083.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore("minda_prefs")

class UserPrefsRepository(private val context: Context) {
    companion object {
        val KEY_ONBOARDED = booleanPreferencesKey("onboarded")
        val KEY_NAME = stringPreferencesKey("user_name")
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { it[KEY_ONBOARDED] ?: false }
    val userName: Flow<String?> = context.dataStore.data.map { it[KEY_NAME] }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { it[KEY_NAME] = name }
    }
    suspend fun setOnboarded(value: Boolean) {
        context.dataStore.edit { it[KEY_ONBOARDED] = value }
    }
}
