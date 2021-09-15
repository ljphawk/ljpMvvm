package com.qiangsheng.respository.network

import com.qiangsheng.base.extensions.ThreadExecutorFactory
import com.qiangsheng.respository.BuildConfig
import com.qiangsheng.respository.commonkey.AppBaseHost
import com.qiangsheng.respository.extensions.GSON
import com.qiangsheng.respository.network.apiresoponse.ApiResponseCallAdapterFactory
import com.qiangsheng.respository.network.interceptor.HeaderInterceptor
import com.qiangsheng.respository.network.interceptor.ResponseInterceptor
import com.qiangsheng.respository.network.livedata.LiveDataCallAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

var OKHTTP: OkHttpClient = initDefaultClient()
var RETROFIT = initDefaultRetrofit()

const val TAG = "Retrofit"

private fun initDefaultClient(): OkHttpClient {
    val dispatcher = Dispatcher(ThreadExecutorFactory.getNetworkExecutor()).apply {
        maxRequests = 100
        maxRequestsPerHost = 64
    }
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    val builder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(ResponseInterceptor())
        .addInterceptor(loggingInterceptor)
        .dispatcher(dispatcher)

    val trustManager = IgnoreTrustManager()
    val sslSocketFactory = createSSLSocketFactory(trustManager)
    if (sslSocketFactory != null) {
        builder.sslSocketFactory(
            sslSocketFactory,
            trustManager
        )
        builder.hostnameVerifier { _, _ -> true }
    }

    //设置禁止抓包
//    if (!BuildConfig.DEBUG) {
//        builder.proxy(Proxy.NO_PROXY)
//    }

    return builder.build()
}

private fun createSSLSocketFactory(trustManager: IgnoreTrustManager): SSLSocketFactory? {
    return try {
        val sc = SSLContext.getInstance("SSL")
        sc.init(null, arrayOf(trustManager), SecureRandom())
        sc.socketFactory
    } catch (e: Exception) {
        null
    }
}

class IgnoreTrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}

fun addInterceptors(vararg interceptors: Interceptor) {
    val newBuilder = OKHTTP.newBuilder()
    for (interceptor in interceptors) {
        newBuilder.addInterceptor(interceptor)
    }

    OKHTTP = newBuilder.build()
    updateRetrofitConfig()
}

fun addNetworkInterceptors(vararg interceptors: Interceptor) {
    val newBuilder = OKHTTP.newBuilder()
    for (interceptor in interceptors) {
        newBuilder.addNetworkInterceptor(interceptor)
    }

    OKHTTP = newBuilder.build()
    updateRetrofitConfig()
}

fun cancelAllRequest() {
    OKHTTP.dispatcher().cancelAll()
}


fun getBaseUrl(): String {
    return AppBaseHost.getApiHost()
}

fun updateRetrofitConfig() {
    RETROFIT = initDefaultRetrofit()
}

private fun initDefaultRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(OKHTTP)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(GSON))
        .build()
}
