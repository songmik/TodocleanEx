package com.example.a23_todoclean.viewmodel.todo

import com.example.a23_todoclean.ViewModelTest
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.domain.todo.InsertToDoUseCase
import com.example.a23_todoclean.presentation.detail.DetailMode
import com.example.a23_todoclean.presentation.detail.DetailViewModel
import com.example.a23_todoclean.presentation.detail.ToDoDetailState
import com.example.a23_todoclean.presentation.list.ListViewModel
import com.example.a23_todoclean.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
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

    private fun initData() = runTest {
        insertToDoUseCase(todo)
    }

    @Test
    fun `test viewModel fetch`() = runTest {
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
    fun `test delete todo`() = runTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.deleteToDo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )
        val listTestObservable = listViewModel.toDoListLiveData.test()

        listViewModel.fetchData()

        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )

        )
    }

    @Test
    fun `test update todo`() = runTest {
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