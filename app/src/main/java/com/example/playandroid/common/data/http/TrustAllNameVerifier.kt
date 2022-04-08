package com.example.playandroid.common.data.http

import android.annotation.SuppressLint
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class TrustAllNameVerifier : HostnameVerifier {
    @SuppressLint("BadHostnameVerifier")
    override fun verify(hostname: String?, session: SSLSession?): Boolean = true

}