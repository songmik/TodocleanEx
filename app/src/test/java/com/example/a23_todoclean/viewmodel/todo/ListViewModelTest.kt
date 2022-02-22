package com.example.a23_todoclean.viewmodel.todo

import com.example.a23_todoclean.ViewModelTest
import com.example.a23_todoclean.data.entity.ToDoEntity
import com.example.a23_todoclean.domain.todo.InsertToDoListUseCase
import com.example.a23_todoclean.presentation.list.ListViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

// LiveViewModel을 테스트하기 위한 Unit Test Class

internal class ListViewModelTest: ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()

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

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    @Test
    fun 'test viewModel fetch'(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )
    }

    @Test
    fun 'test Item Update'(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert()
    }

}