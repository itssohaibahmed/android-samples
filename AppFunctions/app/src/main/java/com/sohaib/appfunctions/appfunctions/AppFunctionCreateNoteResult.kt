package com.sohaib.appfunctions.appfunctions

import androidx.appfunctions.AppFunctionSerializable

/**
 * Result of creating a new note, returned to the calling agent after a successful createNote call.
 */
@AppFunctionSerializable(isDescribedByKDoc = true)
data class AppFunctionCreateNoteResult(
    /** The stable unique identifier of the newly created note. */
    val noteId: Long,
    /** The title that was saved for the note. */
    val title: String,
    /** A short human-readable confirmation message for the user. */
    val message: String,
)