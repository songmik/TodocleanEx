package com.example.a23_todoclean.di

import android.content.Context
import androidx.room.Room
import com.example.a23_todoclean.data.local.db.ToDoDatabase
import com.example.a23_todoclean.data.repository.DefaultToDoRepository
import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.todo.*
import com.example.a23_todoclean.domain.todo.DeleteToDoItemUseCase
import com.example.a23_todoclean.domain.todo.GetToDoItemUseCase
import com.example.a23_todoclean.domain.todo.GetToDoListUseCase
import com.example.a23_todoclean.domain.todo.InsertToDoListUseCase
import com.example.a23_todoclean.domain.todo.InsertToDoUseCase
import com.example.a23_todoclean.presentation.detail.DetailMode
import com.example.a23_todoclean.presentation.detail.DetailViewModel
import com.example.a23_todoclean.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }

    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    viewModel { ListViewModel(get(), get(), get())}
    viewModel { (detailMode: DetailMode, id:Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()