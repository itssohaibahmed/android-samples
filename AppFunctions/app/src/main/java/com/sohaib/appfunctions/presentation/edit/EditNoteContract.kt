package com.sohaib.appfunctions.presentation.edit

data class EditNoteState(
    val noteId: Long = 0L,
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val notFound: Boolean = false,
) {
    val canSave: Boolean get() = title.isNotBlank() && !isSaving && !isLoading && !notFound
}

sealed interface EditNoteIntent {
    data class TitleChanged(val title: String) : EditNoteIntent
    data class ContentChanged(val content: String) : EditNoteIntent
    data object SaveClicked : EditNoteIntent
    data object BackClicked : EditNoteIntent
}

sealed interface EditNoteEffect {
    data object NavigateBack : EditNoteEffect
    data class ShowError(val message: String) : EditNoteEffect
}