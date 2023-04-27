package com.kurly.report.utils

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
class PreferenceWrapper(private val name: String = "") {
    private val mode: Int = Context.MODE_PRIVATE

    private var preferences: SharedPreferences? = null

    fun bind(context: Context): PreferenceWrapper {
        if (preferences == null) {
            preferences = context.applicationContext.getSharedPreferences(name, mode)
        }

        return this
    }

    internal fun getPreferenceOrThrow(): SharedPreferences = preferences ?: throw Error("Context is null!!")
}
