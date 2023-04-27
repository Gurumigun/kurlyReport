package com.kurly.report.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kurly.report.R
import com.kurly.report.databinding.ActivityMainBinding
import com.kurly.report.ui.model.MainViewModel
import com.kurly.report.utils.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var dataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = mainViewModel
        dataBinding.initView()
    }

    private fun ActivityMainBinding.initView() {
        recyclerView.layoutManager?.let {
            val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(it) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    mainViewModel.requestNextSection()
                }
            }
            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }
}