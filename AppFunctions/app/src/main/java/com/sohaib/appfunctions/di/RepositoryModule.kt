package com.sohaib.appfunctions.di

import com.sohaib.appfunctions.data.repository.NotesRepositoryImpl
import com.sohaib.appfunctions.domain.repository.NotesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}