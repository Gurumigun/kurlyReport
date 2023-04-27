package com.kurly.report

import android.app.Application
import com.kurly.report.utils.PreferenceWrapper
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
@HiltAndroidApp
class KurlyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}