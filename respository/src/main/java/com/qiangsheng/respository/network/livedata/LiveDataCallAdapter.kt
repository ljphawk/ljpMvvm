package com.qiangsheng.respository.network.livedata

import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.network.livedata.LiveDataCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class LiveDataCallAdapter(private val responseType:Type) : CallAdapter<ApiResponse<*>, Any> {
    override fun adapt(call: Call<ApiResponse<*>>): Any {
        return LiveDataCall(call)
    }

    override fun responseType(): Type {
        return responseType
    }
}