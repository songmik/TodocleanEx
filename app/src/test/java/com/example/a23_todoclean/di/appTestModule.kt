package com.example.a23_todoclean.di

import com.example.a23_todoclean.data.repository.TestToDoRepository
import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.todo.*
import com.example.a23_todoclean.presentation.detail.DetailMode
import com.example.a23_todoclean.presentation.detail.DetailViewModel
import com.example.a23_todoclean.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    viewModel { ListViewModel(get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(),get(), get(), get()) }

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }



    single<ToDoRepository> { TestToDoRepository() }
}