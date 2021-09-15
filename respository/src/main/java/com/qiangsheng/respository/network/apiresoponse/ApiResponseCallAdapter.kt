package com.qiangsheng.respository.network.apiresoponse

import com.qiangsheng.respository.model.base.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


/*
 *@创建者       L_jp
 *@创建时间     1/25/21 11:16 AM.
 *@描述
 */
class ApiResponseCallAdapter(private val responseType: Type) : CallAdapter<ApiResponse<*>, Call<ApiResponse<*>>> {


    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<ApiResponse<*>>): Call<ApiResponse<*>> {
        return ApiResponseCall(call)
    }
}
