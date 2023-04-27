package com.kurly.report.utils.recyclerview

import androidx.annotation.LayoutRes

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
interface RecyclerViewBindingModel {
    @LayoutRes
    fun layoutId(): Int
    fun bindingVariableId(): Int? = null
    fun requireExecutePendingBindings(): Boolean = false
    fun itemId(): Long? = null
}