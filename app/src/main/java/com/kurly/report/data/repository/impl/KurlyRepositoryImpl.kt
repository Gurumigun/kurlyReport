package com.kurly.report.data.repository.impl

import com.kurly.report.data.KurlyAPI
import com.kurly.report.data.model.*
import com.kurly.report.data.repository.KurlyRepository
import javax.inject.Inject

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
class KurlyRepositoryImpl @Inject constructor(
    private val kurlyService: KurlyAPI
): KurlyRepository {
    override suspend fun getSections(page: Int): APIResult<KurlyResponse<List<Section>>>  = safeApiCall { kurlyService.sections(page) }

    override suspend fun getSectionsProducts(productId: Int): APIResult<KurlyResponse<List<Products>>> = safeApiCall { kurlyService.sectionProducts(productId) }
}