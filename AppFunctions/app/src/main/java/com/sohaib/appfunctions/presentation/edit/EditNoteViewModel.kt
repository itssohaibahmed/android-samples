package com.sohaib.appfunctions.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.appfunctions.domain.repository.NotesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(EditNoteState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EditNoteEffect>()
    val effect: SharedFlow<EditNoteEffect> = _effect.asSharedFlow()

    fun loadNote(noteId: Long) {
        if (_state.value.noteId == noteId && !_state.value.isLoading) return

        viewModelScope.launch {
            _state.update {
                EditNoteState(noteId = noteId, isLoading = true)
            }
            val note = notesRepository.getNote(noteId)
            if (note == null) {
                _state.update { it.copy(isLoading = false, notFound = true) }
                emitEffect(EditNoteEffect.ShowError("Note not found"))
            } else {
                _state.update {
                    it.copy(
                        title = note.title,
                        content = note.content,
                        isLoading = false,
                        notFound = false,
                    )
                }
            }
        }
    }

    fun onIntent(intent: EditNoteIntent) {
        when (intent) {
            is EditNoteIntent.TitleChanged -> _state.update { it.copy(title = intent.title) }
            is EditNoteIntent.ContentChanged -> _state.update { it.copy(content = intent.content) }
            EditNoteIntent.SaveClicked -> saveNote()
            EditNoteIntent.BackClicked -> emitEffect(EditNoteEffect.NavigateBack)
        }
    }

    private fun saveNote() {
        val current = _state.value
        if (!current.canSave) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            notesRepository.updateNote(current.noteId, current.title, current.content)
            emitEffect(EditNoteEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: EditNoteEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}