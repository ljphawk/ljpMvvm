package com.qiangsheng.respository.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.qiangsheng.respository.model.base.ApiResponse

interface INetworkResource<T> {

    @MainThread
    fun createRequest(): LiveData<ApiResponse<T>>

    fun onResponseSuccess(response: ApiResponse<T>) {

    }

    fun onFailed(code: Int, msg: String?) {

    }
}