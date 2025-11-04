package id.antasari.p6miya_230104040083.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_entries ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<DiaryEntry>>

    @Query("SELECT * FROM diary_entries WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): DiaryEntry?

    @Insert suspend fun insert(entry: DiaryEntry): Long
    @Update suspend fun update(entry: DiaryEntry)
    @Delete suspend fun delete(entry: DiaryEntry)
}
