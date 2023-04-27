package com.kurly.report.utils.recyclerview

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

interface PagingAdapterInterface<ITEM: RecyclerViewBindingModel> {
    fun notifyDataSetChanged()
    fun setHasStableIds(enable: Boolean)
    fun notifyItemChanged(position: Int, payload: Any? = null)
    fun notifyItemInserted(position: Int)
    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null)
    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int)
    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int)
    fun notifyItemRemoved(position: Int)
    suspend fun submitData(pagingData: PagingData<ITEM>)
    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<ITEM>)
    fun refresh()
    fun getItemCount(): Int
}


interface ListAdapterInterface<ITEM: RecyclerViewBindingModel> {
    fun getCurrentItems(): List<ITEM>
    fun notifyDataSetChanged()
    fun setHasStableIds(enable: Boolean)
    fun notifyItemChanged(position: Int, payload: Any? = null)
    fun notifyItemInserted(position: Int)
    fun notifyItemRangeChanged(positionStart: Int, itemCount: Int, payloads: Any? = null)
    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int)
    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int)
    fun notifyItemRemoved(position: Int)
    fun submitList(list: List<ITEM>?, commitCallback: Runnable? = null)
}
