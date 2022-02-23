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

    override suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean {
        val foundToDoEntity = toDoList.find { it.id == toDoItem.id }
        return if(foundToDoEntity == null) {
            false
        } else {
            this.toDoList[toDoList.indexOf(foundToDoEntity)] = toDoItem
            true
        }
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        return toDoList.find { it.id == itemId }
    }

    override suspend fun deleteToDoItem(id: Long) {
        val foundToDoEntity = toDoList.find { it.id == id }
        this.toDoList.removeAt(toDoList.indexOf(foundToDoEntity))
    }

    override suspend fun deleteAll() {
        this.toDoList.clear()
    }
}