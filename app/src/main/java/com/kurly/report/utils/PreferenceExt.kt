package com.kurly.report.utils

import android.content.SharedPreferences

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

@Suppress("UNCHECKED_CAST")
operator fun < T> PreferenceWrapper.get(
    key: String,
    default: T?
): T? = when(default) {
    is String -> getPreferenceOrThrow().getString(key, default as String) as T
    is Int -> getPreferenceOrThrow().getInt(key, default as Int) as T
    is Float -> getPreferenceOrThrow().getFloat(key, default as Float) as T
    is Long -> getPreferenceOrThrow().getLong(key, default as Long) as T
    is Boolean -> getPreferenceOrThrow().getBoolean(key, default as Boolean) as T
    else -> default
}

operator fun PreferenceWrapper.set(key: String, value: Any?) {
    when (value) {
        is String? -> getPreferenceOrThrow().edit { it.putString(key, value)  }
        is Int -> getPreferenceOrThrow().edit { it.putInt(key, value) }
        is Boolean -> getPreferenceOrThrow().edit { it.putBoolean(key, value) }
        is Float -> getPreferenceOrThrow().edit { it.putFloat(key, value) }
        is Long -> getPreferenceOrThrow().edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
