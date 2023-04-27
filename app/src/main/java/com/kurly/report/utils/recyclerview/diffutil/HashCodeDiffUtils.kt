package com.kurly.report.utils.recyclerview.diffutil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.kurly.report.utils.recyclerview.RecyclerViewBindingModel

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
class HashCodeDiffUtils : DiffUtil.ItemCallback<RecyclerViewBindingModel>() {
    override fun areItemsTheSame(
        oldItem: RecyclerViewBindingModel,
        newItem: RecyclerViewBindingModel
    ): Boolean = (oldItem.hashCode() == newItem.hashCode())

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: RecyclerViewBindingModel,
        newItem: RecyclerViewBindingModel
    ): Boolean = (oldItem == newItem)
}