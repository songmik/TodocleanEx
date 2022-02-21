package com.example.a23_todoclean.data.repository

import com.example.a23_todoclean.data.entity.ToDoEntity

class TestToDoRepository: ToDoRepository {

    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity>{
        return toDoList
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>){
        this.toDoList.addAll(toDoList)
    }
}