package com.kurly.report.utils

import android.util.Log
import com.kurly.android.mockserver.BuildConfig

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */

const val TAG = "KAKAO_KIY"

fun Any.logD(prefix: String = "") {
    if (BuildConfig.DEBUG) {
        Log.d(TAG, "[$prefix] $this")
    }
}

fun Any.logE(prefix: String = "") {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, "[$prefix] $this")
    }
}

fun Any.logW(prefix: String = "") {
    if (BuildConfig.DEBUG) {
        Log.w(TAG, "[$prefix] $this")
    }
}

fun Any.logI(prefix: String = "") {
    if (BuildConfig.DEBUG) {
        Log.i(TAG, "[$prefix] $this")
    }
}