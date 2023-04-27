package com.kurly.report.data.repository.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kurly.report.data.model.Products
import com.kurly.report.data.repository.PrefRepository
import com.kurly.report.utils.PreferenceWrapper
import com.kurly.report.utils.get
import com.kurly.report.utils.set
import javax.inject.Inject

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
const val PREF_LIKE_ITEMS_KEY = "PREF_LIKE_ITEMS_KEY"
class PrefRepositoryImpl @Inject constructor(
    private val pref: PreferenceWrapper,
    private val gson: Gson
): PrefRepository {
    private val currentLikeItems = mutableListOf<Products>()
    init {
        pref[PREF_LIKE_ITEMS_KEY, ""]?.run {
            if (isNotEmpty()) {
                val itemType = object : TypeToken<List<Products>>() {}.type
                currentLikeItems.addAll(gson.fromJson<List<Products>>(this, itemType))
            }
        }
    }
    override suspend fun insertProduct(products: Products) {
        if (currentLikeItems.none { products.id == it.id }) {
            currentLikeItems.add(products)
            update()
        }
    }

    override suspend fun removeProduct(products: Products) {
        if (currentLikeItems.any { products.id == it.id }) {
            currentLikeItems.remove(products)
            update()
        }
    }

    override suspend fun isLikeProduct(products: Products): Boolean = currentLikeItems.any { products.id == it.id }

    private fun update() {
        pref[PREF_LIKE_ITEMS_KEY] = gson.toJson(currentLikeItems)
    }
}