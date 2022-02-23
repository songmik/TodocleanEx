package com.example.a23_todoclean.viewmodel.todo

import com.example.a23_todoclean.ViewModelTest
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.presentation.list.ListViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@ObsoleteCoroutinesApi
internal class DetailViewModelTest: ViewModelTest() {

    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.DETAIL, id)}
    private val listViewModel: ListViewModel by inject()
    private val insertToDoUseCase: InsertToDoUseCase by inject()

    val id = 1L

    private val todo = ToDoEntity(
        id,
        title = "title 1",
        description = "description 1",
        hasCompleted = false
    )

    @Before
    fun init(){
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoUseCase(todo)
    }

    @Test
    fun 'test viewModel fetch'() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun 'test delete todo'() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.deleteToDo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        val listTestObservable = listViewModel.todoListLiveData.test()

        listViewModel.fetchData()

        listTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(listOf())
            )
        )
    }

    @Test
    fun 'test update todo'() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        val updateTitle = "title 1 update"
        val updateDescription = "description 1 update"

        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )

        detailViewModel.writeToDo(
            title = updateTitle,
            description = updateDescription
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateToDo)
            )
        )
    }
}