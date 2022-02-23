package com.example.a23_todoclean.domain.todo

import com.example.a23_todoclean.data.repository.ToDoRepository
import com.example.a23_todoclean.domain.UseCase

internal class DeleteAllToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke() {
        return toDoRepository.deleteAll()

    }
}