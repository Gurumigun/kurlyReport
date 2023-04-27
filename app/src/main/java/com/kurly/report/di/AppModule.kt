package com.kurly.report.di

import android.content.Context
import com.google.gson.Gson
import com.kurly.report.utils.PreferenceWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providerJson() : Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun providerPreferenceWrapper(@ApplicationContext context: Context) : PreferenceWrapper {
        return PreferenceWrapper("kurly").bind(context)
    }
}