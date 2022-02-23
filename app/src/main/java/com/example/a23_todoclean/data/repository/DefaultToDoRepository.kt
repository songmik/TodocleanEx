package com.example.a23_todoclean.data.repository

import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultToDoRepository(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
): ToDoRepository {

    override suspend fun getToDoList(): List<ToDoEntity> = withContext(ioDispatcher)  {
        toDoDao.getAll()
    }

    override suspend fun getToDoItem(id: Long): ToDoEntity? = withContext(ioDispatcher){
        toDoDao.getById(id)
    }

    override suspend fun insertToDoItem(toDoEntity: ToDoEntity): Long = withContext(ioDispatcher) {
        toDoDao.insert(toDoEntity)
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) = withContext(ioDispatcher){
        toDoDao.insert(toDoList)
    }

    override suspend fun updateToDoItem(toDoEntity: ToDoEntity) = withContext(ioDispatcher) {
        toDoDao.update(toDoEntity)
    }


    override suspend fun deleteToDoItem(id: Long)= withContext(ioDispatcher) {
        toDoDao.delete(id)
    }

    override suspend fun deleteAll()= withContext(ioDispatcher) {
        toDoDao.deleteAll()
    }
}