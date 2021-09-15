package com.qiangsheng.respository.network.interceptor

import com.qiangsheng.respository.extensions.parseObject
import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.network.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.nio.charset.StandardCharsets

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()
        val response = chain.proceed(newRequestBuilder.build())
        if (!response.isSuccessful) {
            return response
        }

        //统一对状态码进行处理
        response.body()?.let {
            val contentLength = it.contentLength()
            val contentType = it.contentType()
            val charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            val source = it.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer()

            if ("gzip".equals(response.headers()["Content-Encoding"], ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            if (contentLength != 0L) {
                errCodeHandler(buffer.clone().readString(charset))
            }
        }
        return response
    }

    private fun errCodeHandler(bodyJson: String) {
        parseObject(bodyJson, ApiResponse::class.java)?.let {
            if (!it.isSuccessful()) {
                throw ApiException(it.result, it.message ?: "")
            }
        }
    }
}