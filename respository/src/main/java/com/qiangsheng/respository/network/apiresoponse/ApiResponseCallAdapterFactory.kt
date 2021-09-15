package com.qiangsheng.respository.network.apiresoponse

import com.qiangsheng.respository.model.base.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        if (rawType != Call::class.java) {//Call
            return null
        }

        if (returnType !is ParameterizedType) {//Call<?>
            throw IllegalStateException("Call return type must be parameterized" + " as Call<Foo> or Call<? extends Foo>")
        }

        val apiResponseType = getParameterUpperBound(0, returnType)
        val rawLiveType = getRawType(apiResponseType)

        if (rawLiveType != ApiResponse::class.java) {
            return null
        }

        if (apiResponseType !is ParameterizedType) {
            throw IllegalStateException("ApiResponse return type must be parameterized" + " as ApiResponse<Foo> or ApiResponse<? extends Foo>")
        }

        return ApiResponseCallAdapter(apiResponseType)
    }
}