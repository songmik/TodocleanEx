package com.example.a23_todoclean.presentation.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.a23_todoclean.presentation.BaseActivity
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.core.parameter.parametersOf

internal class DetailActivity: BaseActivity<DetailViewModel>() {

    private lateinit var binding: ActivityDetailBinding

    override val viewModel: DetailViewModel by viewModel{
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongArrayExtra(TODO_ID_KEY, -1)
        )
    }

    companion object{
        const val TODO_ID_KEY = "ToDoId"
        const val DETAIL_MODE_KEY = "DetailMode"
        const val FETCH_REQUEST_CODE = 10

        fun getIntent(context: Context, detailMode: DetailMode) = Intent(context, DetailActivity::class.java).apply {
            putExtra(DETAIL_MODE_KEY, detailMode)
        }

        fun getIntent(context: Context, id:Long, detailMode: DetailMode) = Intent(context, DetailActivity::class.java).apply {
            putExtra(TODO_ID_KEY, id)
            putExtra(DETAIL_MODE_KEY, detailMode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setResult(Activity.RESULT_OK)
    }

    override fun observeData()  =viewModel.toDoDetailLiveData.observe(this@DetailActivity) {
        when(it){
            is ToDoDetailState.UnInitialized -> {
                initViews(binding)
            }
            is ToDoDetailState.Loading -> {
                handleLoadingState()
            }
            is ToDoDetailState.Success -> {
                handleSuccessState(it)
            }
            is ToDoDetailState.Modify -> {
                handleModifyState()
            }
            is ToDoDetailState.Delete -> {
                Toast.makeText(this, "성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            is ToDoDetailState.Error -> {
                Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            is ToDoDetailState.Write -> {
                handleWriteState()
            }
        }
    }

    private fun initViews(binding: ActivityDetailBinding) = with(binding){

    }

    private fun handleLoadingState() = with(binding){

    }

    private fun handleModifyState() = with(binding){

    }

    private fun handleWriteState() = with(binding){

    }

    private fun handleSuccessState(state: ToDoDetailState.Success) = with(binding){

    }
}