package com.kurly.report.ui.uimodel

import com.kurly.report.BR
import com.kurly.report.R
import com.kurly.report.data.model.Products
import com.kurly.report.utils.recyclerview.RecyclerViewBindingModel
import com.kurly.report.utils.recyclerview.state.ListRecyclerViewState

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
    val data: Products
) : RecyclerViewBindingModel {
    
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_vertical_products
}

data class HorizontalProductUIModel(
    val data: Products
) : RecyclerViewBindingModel {

    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_horizontal_products
}

data class GridProductUIModel(
    val recyclerViewState: ListRecyclerViewState<RecyclerViewBindingModel>
) : RecyclerViewBindingModel {

    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_grid_list
}