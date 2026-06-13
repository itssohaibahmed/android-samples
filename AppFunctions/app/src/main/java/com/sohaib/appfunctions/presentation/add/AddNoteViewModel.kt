package com.sohaib.appfunctions.presentation.add

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

class AddNoteViewModel(
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddNoteState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddNoteEffect>()
    val effect: SharedFlow<AddNoteEffect> = _effect.asSharedFlow()

    fun onIntent(intent: AddNoteIntent) {
        when (intent) {
            is AddNoteIntent.TitleChanged -> _state.update { it.copy(title = intent.title) }
            is AddNoteIntent.ContentChanged -> _state.update { it.copy(content = intent.content) }
            AddNoteIntent.SaveClicked -> saveNote()
            AddNoteIntent.BackClicked -> emitEffect(AddNoteEffect.NavigateBack)
        }
    }

    private fun saveNote() {
        val current = _state.value
        if (!current.canSave) return

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            notesRepository.addNote(current.title, current.content)
            emitEffect(AddNoteEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: AddNoteEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
