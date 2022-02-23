package com.example.a23_todoclean.data.repository

import com.example.a23_todoclean.data.entity.ToDoEntity

class DefaultToDoRepository: ToDoRepository {

    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToDoItem(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}