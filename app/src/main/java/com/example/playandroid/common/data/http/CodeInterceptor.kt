package com.example.playandroid.common.data.http

import okhttp3.Interceptor
import okhttp3.Response

class CodeInterceptor: Interceptor {
    private  val TAG = "CodeInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return chain.proceed(chain.request())
    }
}