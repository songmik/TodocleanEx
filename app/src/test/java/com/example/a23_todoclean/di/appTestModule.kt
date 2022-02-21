package com.example.a23_todoclean.di

import com.example.a23_todoclean.data.repository.TestToDoRepository
import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.todo.GetToDoListUseCase
import com.example.a23_todoclean.domain.todo.InsertToDoListUseCase
import com.example.a23_todoclean.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    viewModel { ListViewModel(get()) }

    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }

    single<ToDoRepository> { TestToDoRepository() }
}