package com.sohaib.appfunctions.domain.repository

import com.sohaib.appfunctions.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun getNote(id: Long): Note?
    suspend fun addNote(title: String, content: String): Long
    suspend fun updateNote(id: Long, title: String, content: String)
    suspend fun deleteNote(id: Long)
}