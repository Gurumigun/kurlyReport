package com.kurly.report.data.repository

import com.kurly.report.data.model.APIResult
import com.kurly.report.data.model.KurlyResponse
import com.kurly.report.data.model.Products
import com.kurly.report.data.model.Section

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
interface KurlyRepository {
    suspend fun getSections(page: Int): APIResult<KurlyResponse<List<Section>>>

    suspend fun getSectionsProducts(productId: Int): APIResult<KurlyResponse<List<Products>>>
}