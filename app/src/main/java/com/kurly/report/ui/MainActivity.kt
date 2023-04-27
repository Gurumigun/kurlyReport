package com.kurly.report.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kurly.report.R
import com.kurly.report.databinding.ActivityMainBinding
import com.kurly.report.ui.model.MainViewModel
import com.kurly.report.utils.EndlessRecyclerViewScrollListener
import com.kurly.report.utils.collectLatest
import com.kurly.report.utils.showToast
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
        mainViewModel.initViewModel()
    }

    private fun ActivityMainBinding.initView() {
        pullToRefresh.setOnRefreshListener {
            pullToRefresh.isRefreshing = false
            mainViewModel.refresh()
        }
        recyclerView.layoutManager?.let {
            val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(it) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    mainViewModel.requestSection()
                }
            }
            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }

    private fun MainViewModel.initViewModel() {
        collectLatest(showUserNotification) {
            when (it) {
                is SendUserNotification.Alert -> AlertDialog.Builder(baseContext)
                    .apply {
                        title = getString(R.string.alert_title)
                        setMessage(it.message)
                        setPositiveButton(getString(R.string.alert_positive)) { dialog, _ -> dialog.dismiss() }
                    }
                    .show()
                is SendUserNotification.Toast -> showToast(it.message)
            }
        }
    }
}