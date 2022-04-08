package com.example.playandroid.common.di

import com.example.playandroid.BuildConfig
import com.example.playandroid.common.data.http.HttpService
import com.example.playandroid.common.data.http.TrustAllNameVerifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIME_OUT_SECONDS = 10

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    // SingletonComponent 全局单例
    // singleton 单例

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): HttpService =
        retrofit.create(HttpService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HttpService.url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .hostnameVerifier(TrustAllNameVerifier())
            .build()
    }
}