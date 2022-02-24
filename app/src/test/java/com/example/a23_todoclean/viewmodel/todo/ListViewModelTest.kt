package com.example.a23_todoclean.viewmodel.todo

import com.example.a23_todoclean.ViewModelTest
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.domain.todo.GetToDoItemUseCase
import com.example.a23_todoclean.domain.todo.InsertToDoListUseCase
import com.example.a23_todoclean.domain.todo.InsertToDoUseCase
import com.example.a23_todoclean.presentation.list.ListViewModel
import com.example.a23_todoclean.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

// LiveViewModel을 테스트하기 위한 Unit Test Class

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }


    @Before
    fun init(){
        initData()

    }

    private fun initData() = runTest {
        insertToDoListUseCase(mockList)
    }

    @Test
    fun `test viewModel fetch`(): Unit = runTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(mockList)
            )
        )
    }

    @Test
    fun `test Item Update`(): Unit = runTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted?: false == todo.hasCompleted)
    }

    @Test
    fun `test Item Delete All`(): Unit = runTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())

            )

        )
    }

}