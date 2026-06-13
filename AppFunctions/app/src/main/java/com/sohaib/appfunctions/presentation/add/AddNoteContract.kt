package com.sohaib.appfunctions.presentation.add

data class AddNoteState(
    val title: String = "",
    val content: String = "",
    val isSaving: Boolean = false,
) {
    val canSave: Boolean get() = title.isNotBlank() && !isSaving
}

sealed interface AddNoteIntent {
    data class TitleChanged(val title: String) : AddNoteIntent
    data class ContentChanged(val content: String) : AddNoteIntent
    data object SaveClicked : AddNoteIntent
    data object BackClicked : AddNoteIntent
}

sealed interface AddNoteEffect {
    data object NavigateBack : AddNoteEffect
}
