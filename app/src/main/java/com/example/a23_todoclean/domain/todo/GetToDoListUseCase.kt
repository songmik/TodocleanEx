package com.example.a23_todoclean.domain.todo

import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.UseCase

internal class GetToDoListUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke():List<ToDoEntity> {
        return toDoRepository.getToDoList()

    }
}