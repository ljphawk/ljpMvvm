package com.qiangsheng.respository.network.apiresoponse

import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.network.ApiException
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*
 *@创建者       L_jp
 *@创建时间     1/25/21 11:24 AM.
 *@描述
 */
internal class ApiResponseCall(private val call: Call<ApiResponse<*>>) : Call<ApiResponse<*>> {
    override fun enqueue(callback: Callback<ApiResponse<*>>) {

        call.enqueue(object : Callback<ApiResponse<*>> {
            override fun onFailure(call: Call<ApiResponse<*>>, t: Throwable) {
                val response = ApiResponse<Nothing>()
                //自己定义的异常,(自己服务器返回的错误码,详见ResponseInterceptor中的throw)
                if (t is ApiException) {
                    response.result = t.errorCode
                    response.message = t.errorMsg
                } else {
                    response.result = -1
                    response.message = t.message
                }
                callback.onResponse(this@ApiResponseCall, Response.success(response))
            }

            override fun onResponse(
                call: Call<ApiResponse<*>>,
                response: Response<ApiResponse<*>>
            ) {
                response.isSuccessful
                val body = response.body() ?: ApiResponse<Nothing>(
                    result = response.code(),
                    message = response.message()
                )
                callback.onResponse(this@ApiResponseCall, Response.success(body))
            }
        })
    }


    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun clone(): Call<ApiResponse<*>> {
        return ApiResponseCall(call.clone())
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun cancel() {
        call.cancel()
    }


    override fun execute(): Response<ApiResponse<*>> {
        throw UnsupportedOperationException("ApiResponseCall does not support synchronous execution")
    }

    override fun request(): Request {
        return call.request()
    }

}