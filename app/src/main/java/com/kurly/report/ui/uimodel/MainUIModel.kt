package com.kurly.report.ui.uimodel

import com.kurly.report.BR
import com.kurly.report.R
import com.kurly.report.data.model.Products
import com.kurly.report.utils.recyclerview.RecyclerViewBindingModel

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

data class SectionUIModel(
    val title: String
) : RecyclerViewBindingModel {
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_section_title
}

data class VerticalProductUIModel(
    private val data: Products
) : RecyclerViewBindingModel {
    
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_vertical_products
}