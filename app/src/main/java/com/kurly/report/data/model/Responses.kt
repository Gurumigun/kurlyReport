package com.kurly.report.data.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

data class Page(
    @SerializedName("next_page") val nextPage: Int
)

data class KurlyResponse<T>(
    @SerializedName("data") val data: T,
    @SerializedName("paging") val page: Page?
)

data class Section(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)

data class Products(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("originalPrice") val originalPrice: Int,
    @SerializedName("discountedPrice") val discountedPrice: Int?,
    @SerializedName("isSoldOut") val isSoldOut: Boolean
)