package com.kurly.report.data.repository

import com.kurly.report.data.model.Products

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
interface PrefRepository {
    suspend fun insertProduct(products: Products)
    suspend fun removeProduct(products: Products)
    suspend fun isLikeProduct(products: Products): Boolean
}