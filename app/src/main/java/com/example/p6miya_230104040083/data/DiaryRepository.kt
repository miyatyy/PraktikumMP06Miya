package id.antasari.p6miya_230104040083.data

import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val dao: DiaryDao) {
    fun getAllFlow(): Flow<List<DiaryEntry>> = dao.getAllFlow()
    suspend fun getById(id: Long) = dao.getById(id)
    suspend fun add(entry: DiaryEntry): Long = dao.insert(entry)
    suspend fun update(entry: DiaryEntry) = dao.update(entry)
    suspend fun remove(entry: DiaryEntry) = dao.delete(entry)
}
