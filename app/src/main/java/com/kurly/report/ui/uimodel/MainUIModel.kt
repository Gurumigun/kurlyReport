package com.kurly.report.ui.uimodel

import com.kurly.report.BR
import com.kurly.report.R
import com.kurly.report.data.model.Products
import com.kurly.report.utils.recyclerview.RecyclerViewBindingModel
import com.kurly.report.utils.recyclerview.state.ListRecyclerViewState
import kotlinx.coroutines.flow.MutableStateFlow

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

class VerticalProductUIModel(
    _data: Products,
    isLikeItem: Boolean,
    val likeAction: (ProductBase) -> Unit
) : ProductBase(_data, isLikeItem), RecyclerViewBindingModel {
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_vertical_products
}


class HorizontalProductUIModel(
    _data: Products,
    isLikeItem: Boolean,
    val likeAction: (ProductBase) -> Unit
) : ProductBase(_data, isLikeItem), RecyclerViewBindingModel {
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_horizontal_products
}

open class ProductBase(val data: Products, isLikeItem: Boolean) {
    val isLike = MutableStateFlow(false)
    init {
        isLike.value = isLikeItem
    }
}

data class GridProductUIModel(
    val recyclerViewState: ListRecyclerViewState<RecyclerViewBindingModel>
) : RecyclerViewBindingModel {
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_grid_list
}

data class HorizontalListUIModel(
    val recyclerViewState: ListRecyclerViewState<RecyclerViewBindingModel>
) : RecyclerViewBindingModel {
    override fun bindingVariableId(): Int = BR.uiModel
    override fun layoutId(): Int = R.layout.item_horizontal_list
}