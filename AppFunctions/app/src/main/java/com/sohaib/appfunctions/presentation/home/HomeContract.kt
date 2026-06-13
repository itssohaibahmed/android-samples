package com.sohaib.appfunctions.presentation.home

import com.sohaib.appfunctions.domain.model.Note

data class HomeState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
)

sealed interface HomeIntent {
    data object AddNoteClicked : HomeIntent
    data class EditNoteClicked(val noteId: Long) : HomeIntent
    data class DeleteNote(val noteId: Long) : HomeIntent
}

sealed interface HomeEffect {
    data object NavigateToAdd : HomeEffect
    data class NavigateToEdit(val noteId: Long) : HomeEffect
}