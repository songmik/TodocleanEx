package com.example.a23_todoclean.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.domain.todo.DeleteAllToDoItemUseCase
import com.example.a23_todoclean.domain.todo.GetToDoListUseCase
import com.example.a23_todoclean.domain.todo.UpdateToDoUseCase
import com.example.a23_todoclean.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ListViewModel (
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase
): BaseViewModel(){

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val toDoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch{
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch {
        updateToDoUseCase(toDoEntity)
    }

    fun deleteAll() = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        deleteAllToDoItemUseCase
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
    }
}