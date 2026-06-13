package com.sohaib.appfunctions.data.repository

import com.sohaib.appfunctions.data.local.NoteDao
import com.sohaib.appfunctions.data.local.NoteEntity
import com.sohaib.appfunctions.data.mapper.toDomain
import com.sohaib.appfunctions.domain.model.Note
import com.sohaib.appfunctions.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val noteDao: NoteDao,
) : NotesRepository {

    override fun observeNotes(): Flow<List<Note>> = noteDao.observeAllNotes().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getNote(id: Long): Note? = noteDao.getNoteById(id)?.toDomain()

    override suspend fun addNote(title: String, content: String): Long {
        val now = System.currentTimeMillis()
        return noteDao.insert(
            NoteEntity(
                title = title.trim(),
                content = content.trim(),
                updatedAt = now,
            ),
        )
    }

    override suspend fun updateNote(id: Long, title: String, content: String) {
        val existing = noteDao.getNoteById(id) ?: return
        noteDao.update(
            existing.copy(
                title = title.trim(),
                content = content.trim(),
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun deleteNote(id: Long) {
        noteDao.deleteById(id)
    }
}