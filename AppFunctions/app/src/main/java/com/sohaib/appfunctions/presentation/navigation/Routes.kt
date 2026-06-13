package com.sohaib.appfunctions.presentation.navigation

object Routes {
    const val HOME = "home"
    const val ADD = "add"
    const val EDIT = "edit/{noteId}"

    fun edit(noteId: Long): String = "edit/$noteId"
}