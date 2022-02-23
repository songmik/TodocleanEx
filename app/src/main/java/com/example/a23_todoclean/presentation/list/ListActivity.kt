package com.example.a23_todoclean.presentation.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.a23_todoclean.presentation.BaseActivity
import com.example.a23_todoclean.presentation.detail.DetailActivity
import com.example.a23_todoclean.presentation.view.ToDoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.bind
import kotlin.coroutines.CoroutineContext

internal class ListActivity: BaseActivity<ListViewModel>(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityListBinding

    private val adapter = ToDoAdapter()

    override val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeData() {
        viewModel.todoListLiveData.observe(this){
            when(it){
                is ToDoListState.UnInitialized -> {
                    initViews(binding)
                }
                is ToDoListState.Loading -> {
                    handleLoadingState()
                }
                is ToDoListState.Success -> {
                    handleSuccessState(it)
                }
                is ToDoListState.Error -> { }
            }
        }
    }

    private fun initViews(binding: ActivityListBinding) = with(binding){

    }

    private fun handleLoadingState() = with(binding){

    }

    private fun handleSuccessState(state: ToDoListState.Success) = with(binding){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            viewModel.fetchData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {

        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate()
        return true
    }


}