package com.qiangsheng.respository.network.livedata

import androidx.lifecycle.LiveData
import com.qiangsheng.base.utils.LOG
import com.qiangsheng.respository.R
import com.qiangsheng.respository.app.BaseApplication
import com.qiangsheng.respository.model.base.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class LiveDataCall(private val originCall: Call<ApiResponse<*>>) :
    LiveData<ApiResponse<*>>(),
    Callback<ApiResponse<*>> {

    private val TAG = "ApiResponseLiveData"

    override fun onActive() {
        if (originCall.isCanceled || originCall.isExecuted) {
            return
        }
        originCall.enqueue(this)
    }

    override fun onInactive() {
        if (originCall.isCanceled) {
            return
        }
        originCall.cancel()
    }

    override fun onFailure(call: Call<ApiResponse<*>>, t: Throwable) {
        if (originCall.isCanceled) {
            return
        }
        postValue(
            ApiResponse<Nothing>(
                result = -1,
                message = BaseApplication.instance.getString(R.string.requestNetFaild)
            )
        )
        LOG.e(TAG, "onFailed ", t)
    }

    override fun onResponse(call: Call<ApiResponse<*>>, response: Response<ApiResponse<*>>) {
        val body = response.body()
        if (response.isSuccessful) {
            postValue(body)
        } else {
            if (body != null) {
                postValue(body)
                LOG.e(TAG, "onResponse isFailed code ${body.result}, message ${body.message}")
            } else {
                postValue(
                    ApiResponse<Nothing>(
                        result = response.code(),
                        message = response.message()
                    )
                )
                LOG.e(
                    TAG,
                    "onResponse isFailed code ${response.code()}, message ${response.message()}"
                )
            }
        }
    }
}