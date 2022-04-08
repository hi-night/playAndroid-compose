package com.example.playandroid.common.di

import android.app.Application
import com.example.playandroid.MyApplication
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    fun provideApplication(application: Application): MyApplication {
        return application as MyApplication
    }
}