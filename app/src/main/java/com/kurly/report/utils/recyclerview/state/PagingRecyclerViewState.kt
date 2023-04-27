package com.kurly.report.utils.recyclerview.state

import android.os.Handler
import android.os.Looper
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import com.kurly.report.utils.recyclerview.PagingAdapterInterface
import com.kurly.report.utils.recyclerview.RecyclerViewBindingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
class PagingRecyclerViewState<ITEM : RecyclerViewBindingModel>(private val coroutineScope: CoroutineScope, val diffUtil: DiffUtil.ItemCallback<ITEM>)  {
    private val handler = Handler(Looper.getMainLooper())

    private var _currentList = MutableStateFlow<PagingData<ITEM>>(PagingData.empty())
    var currentList: StateFlow<PagingData<ITEM>> =  _currentList

    var loadState = MutableStateFlow<LoadState>(LoadState.Loading)

    var adapterDependency: PagingAdapterInterface<ITEM>? = null
        private set

    fun setHasStableIds(enable: Boolean) {
        handler.post {
            adapterDependency?.setHasStableIds(enable)
        }
    }

    fun refresh() {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.refresh()
        }
    }

    fun notifyDataSetChanged() {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyDataSetChanged()
        }
    }

    fun notifyItemChanged(position: Int, payload: Any?) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemChanged(position, payload)
        }
    }

    fun notifyItemInserted(position: Int) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemInserted(position)
        }
    }

    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any?) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemRangeChanged(positionStart, itemCount, payloads)
        }
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemRangeInserted(positionStart, itemCount)
        }
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    fun notifyItemRemoved(position: Int) {
        handler.post {
            val adapter = adapterDependency ?: return@post
            adapter.notifyItemRemoved(position)
        }
    }

    fun submitData(list: PagingData<ITEM>) {
        handler.post {
            coroutineScope.launch {
                adapterDependency?.submitData(list)
            }
        }
    }

    fun setAdapterDependency(adapterDependency: PagingAdapterInterface<ITEM>?) {
        handler.post {
            this.adapterDependency = adapterDependency
            coroutineScope.launch {
                _currentList.collectLatest {
                    submitData(it)
                }
            }
        }
    }

    fun collectLatest(it: PagingData<ITEM>) {
        _currentList.value = it
    }

    fun getItemCount(): Int = adapterDependency?.getItemCount() ?: 0
}