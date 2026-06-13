package com.sohaib.appfunctions.di

import androidx.room.Room
import com.sohaib.appfunctions.data.local.NotesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(androidContext(), NotesDatabase::class.java, "notes.db").build() }
    single { get<NotesDatabase>().noteDao() }
}