package com.qiangsheng.respository.network.interceptor

import com.qiangsheng.respository.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()
        newRequestBuilder.addHeader("User-Agent", "Android")
        newRequestBuilder.addHeader("version", BuildConfig.VERSION_CODE.toString())

        return chain.proceed(newRequestBuilder.build())
    }

}