package com.kurly.report.utils

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kurly.report.data.model.APIResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */


fun View.findFragmentOrNull(): Fragment? {
    return try {
        findFragment()
    } catch (e: IllegalStateException) {
        null
    }
}

fun View.findActivityOrNull(): ComponentActivity? {
    var context: Context = context
    while (context is ContextWrapper) {
        if (context is ComponentActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.findLifecycleOwner(): LifecycleOwner? {
    return this.findFragmentOrNull()?.viewLifecycleOwner ?: findActivityOrNull()
}

suspend fun <T> APIResult<T>.onSuspendOnSuccess(block: suspend (T) -> Unit): APIResult<T> {
    if (this is APIResult.Success) {
        block(data)
    }
    return this
}

suspend fun <T> APIResult<T>.onSuspendFailure(block: suspend (APIResult.Failure) -> Unit): APIResult<T> {
    if (this is APIResult.Failure) {
        block(this)
    }
    return this
}

fun <T> APIResult<T>.toFlow(): Flow<T> {
    return when (this) {
        is APIResult.Success -> flow { emit(data) }
        is APIResult.Failure -> flow { throw Exception(message) }
        is APIResult.NetworkError -> flow { throw error }
    }
}

fun LoadState?.isLoading(): Boolean = this is LoadState.Loading

fun Context.dpToPx(dp: Int): Float {
    return dp * resources.displayMetrics.density
}

fun Context.displayWidth(): Int = resources.displayMetrics.widthPixels
