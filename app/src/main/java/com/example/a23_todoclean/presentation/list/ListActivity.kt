package com.example.a23_todoclean.presentation.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a23_todoclean.R
import com.example.a23_todoclean.databinding.ActivityListBinding
import com.example.a23_todoclean.presentation.BaseActivity
import com.example.a23_todoclean.presentation.detail.DetailActivity
import com.example.a23_todoclean.presentation.detail.DetailMode
import com.example.a23_todoclean.presentation.view.ToDoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.viewmodel.ext.android.viewModel
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
        viewModel.toDoListLiveData.observe(this){
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
        recyclerView.layoutManager = LinearLayoutManager(this@ListActivity, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        addToDoButton.setOnClickListener {
            startActivityForResult(
                DetailActivity.getIntent(this@ListActivity, DetailMode.WRITE),
                DetailActivity.FETCH_REQUEST_CODE
            )
        }
    }

    private fun handleLoadingState() = with(binding){
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ToDoListState.Success) = with(binding){
        refreshLayout.isEnabled = state.toDoList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if(state.toDoList.isEmpty()){
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setToDoList(
                state.toDoList,
                toDoItemClickListener = {
                    startActivityForResult(
                        DetailActivity.getIntent(this@ListActivity, it.id, DetailMode.DETAIL),
                        DetailActivity.FETCH_REQUEST_CODE
                    )
                }, toDoCheckListener = {
                    viewModel.updateEntity(it)
                }
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            viewModel.fetchData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }


}