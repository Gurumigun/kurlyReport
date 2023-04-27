package com.kurly.report.data.repository

import androidx.paging.PagingData
import com.kurly.report.data.model.APIResult
import com.kurly.report.data.model.KurlyResponse
import com.kurly.report.data.model.Products
import com.kurly.report.data.model.Section
import kotlinx.coroutines.flow.Flow

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
interface KurlyRepository {
    suspend fun getSections(page: Int): APIResult<KurlyResponse<List<Section>>>

    suspend fun getSectionsProducts(productId: Int): APIResult<KurlyResponse<List<Products>>>
}