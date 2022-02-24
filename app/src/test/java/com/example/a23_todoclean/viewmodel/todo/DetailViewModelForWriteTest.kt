package com.example.a23_todoclean.viewmodel.todo

import com.example.a23_todoclean.ViewModelTest
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.presentation.detail.DetailMode
import com.example.a23_todoclean.presentation.detail.DetailViewModel
import com.example.a23_todoclean.presentation.detail.ToDoDetailState
import com.example.a23_todoclean.presentation.list.ListViewModel
import com.example.a23_todoclean.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {

    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.WRITE, id)  }
    private val listViewModel: ListViewModel by inject()

    val id = 0L

    private val todo = ToDoEntity(
        id,
        title = "title 1",
        description = "description 1",
        hasCompleted = false
    )

    @Test
    fun `test viewModel fetch`() = runTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()
        val listTestObservable = listViewModel.toDoListLiveData.test()

        detailViewModel.writeToDo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )

        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf(todo))
            )
        )
    }

}