package com.example.a23_todoclean.domain.todo

import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.UseCase

internal class UpdateToDoUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(toDoEntity: ToDoEntity) {
        return toDoRepository.updateToDoItem(toDoEntity)
    }
}