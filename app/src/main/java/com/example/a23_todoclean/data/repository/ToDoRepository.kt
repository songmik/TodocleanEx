package com.example.a23_todoclean.data.repository

import com.example.a23_todoclean.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteToDoItem(id: Long)

    suspend fun deleteAll()
}