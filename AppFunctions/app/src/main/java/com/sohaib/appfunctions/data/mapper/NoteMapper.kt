package com.sohaib.appfunctions.data.mapper

import com.sohaib.appfunctions.data.local.NoteEntity
import com.sohaib.appfunctions.domain.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    updatedAt = updatedAt,
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    updatedAt = updatedAt,
)