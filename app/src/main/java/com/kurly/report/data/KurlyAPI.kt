package com.kurly.report.data

import com.kurly.report.data.model.KurlyResponse
import com.kurly.report.data.model.Products
import com.kurly.report.data.model.Section
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
interface KurlyAPI {
    @GET("sections")
    suspend fun sections(@Query("page") page: Int): Response<KurlyResponse<List<Section>>>

    @GET("/section/products")
    suspend fun sectionProducts(@Query("sectionId") sectionId: Int): Response<KurlyResponse<List<Products>>>
}