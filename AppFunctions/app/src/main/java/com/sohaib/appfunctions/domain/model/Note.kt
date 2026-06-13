package com.sohaib.appfunctions.domain.model

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val updatedAt: Long,
)