package id.antasari.p6miya_230104040083.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val mood: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
