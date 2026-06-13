package com.sohaib.appfunctions.appfunctions

import androidx.appfunctions.AppFunctionContext
import androidx.appfunctions.AppFunctionInvalidArgumentException
import androidx.appfunctions.service.AppFunction
import com.sohaib.appfunctions.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * App Functions that expose note capabilities to AI agents and system assistants.
 */
class NotesAppFunctions(
    private val notesRepository: NotesRepository,
) {

    /**
     * Creates a new note and saves it locally in the Notes app.
     *
     * Call this when the user asks to create, save, add, jot down, or remember something as a note.
     * Parse the user's natural-language request into a short title and a content body:
     * - title: a concise summary or the first sentence of what the user wants to remember.
     * - content: the full details the user mentioned; use an empty string if everything fits in the title.
     *
     * Example: for "Create a note that I need to buy milk and eggs tomorrow", use
     * title "Buy groceries" and content "Buy milk and eggs tomorrow".
     *
     * @param title A non-blank short title for the note.
     * @param content The note body text; pass an empty string if the title alone captures everything.
     * @return Confirmation with the new note id and saved title.
     * @throws AppFunctionInvalidArgumentException If title is blank after trimming.
     */
    @AppFunction(isDescribedByKDoc = true)
    suspend fun createNote(
        context: AppFunctionContext,
        title: String,
        content: String,
    ): AppFunctionCreateNoteResult = withContext(Dispatchers.IO) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) {
            throw AppFunctionInvalidArgumentException("title must not be blank.")
        }

        val noteId = notesRepository.addNote(trimmedTitle, content.trim())
        AppFunctionCreateNoteResult(
            noteId = noteId,
            title = trimmedTitle,
            message = "Note \"$trimmedTitle\" created successfully.",
        )
    }
}