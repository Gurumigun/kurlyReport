package com.kurly.report.di

import android.content.Context
import com.google.gson.Gson
import com.kurly.android.mockserver.MockInterceptor
import com.kurly.report.data.KurlyAPI
import com.kurly.report.data.repository.KurlyRepository
import com.kurly.report.data.repository.PrefRepository
import com.kurly.report.data.repository.impl.KurlyRepositoryImpl
import com.kurly.report.data.repository.impl.PrefRepositoryImpl
import com.kurly.report.utils.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(MockInterceptor(context))
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): KurlyAPI {
        return retrofit.create(KurlyAPI::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindKurlyRepository(repositoryImpl: KurlyRepositoryImpl): KurlyRepository
    @Singleton
    @Binds
    abstract fun bindPrefRepository(repositoryImpl: PrefRepositoryImpl): PrefRepository
}