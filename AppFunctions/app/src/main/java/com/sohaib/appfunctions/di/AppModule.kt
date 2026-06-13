package com.sohaib.appfunctions.di

import com.sohaib.appfunctions.presentation.add.AddNoteViewModel
import com.sohaib.appfunctions.presentation.edit.EditNoteViewModel
import com.sohaib.appfunctions.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { EditNoteViewModel(get()) }
}