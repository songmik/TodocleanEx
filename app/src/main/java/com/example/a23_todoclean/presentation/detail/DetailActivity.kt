package com.example.a23_todoclean.presentation.detail

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
}