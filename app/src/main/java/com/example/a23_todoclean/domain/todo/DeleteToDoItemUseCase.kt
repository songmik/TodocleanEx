package com.example.a23_todoclean.domain.todo

import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.UseCase

internal class DeleteToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long) {
        return toDoRepository.deleteToDoItem(id)

    }
}