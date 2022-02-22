package com.example.a23_todoclean.presentation.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.domain.todo.GetToDoListUseCase
import com.example.a23_todoclean.domain.todo.UpdateToDoUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ListViewModel (
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase
): ViewModel(){

    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>(listOf())
    val todoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData

    @SuppressLint("NullSafeMutableLiveData")
    fun fetchData(): Job = viewModelScope.launch{
        _toDoListLiveData.postValue(getToDoListUseCase())
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
        val success = updateToDoUseCase(toDoEntity)
    }
}