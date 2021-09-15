package com.qiangsheng.respository.network

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.qiangsheng.base.extensions.ThreadExecutorFactory
import com.qiangsheng.base.extensions.uiThread
import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.model.base.ListData

/**
 * A 表示 APi返回的
 * R 表示最终的结果 Result
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
abstract class NetworkBoundResource<R, A> : INetworkBoundResource<R, A> {
    private val liveData = MediatorLiveData<Resource<R>>()
    private var initialed = false

    //cache info.
    private var status = Status.LOADING
    private var msg: String? = null
    private var code: Int = 0

    private val cacheSource by lazy {
        loadFromCache()
    }

    private fun init() {
        liveData.value = Resource(status = Status.LOADING)

        //join cache
        liveData.addSource(cacheSource) {
            liveData.removeSource(cacheSource)
            if (shouldRequest(it)) {
                loadFromNetwork()
            } else {
                liveData.addSource(cacheSource) { result ->
                    liveData.value = Resource(data = result, status = Status.SUCCESS)
                }
            }
        }
    }

    private fun resetStatus() {
        status = Status.LOADING
        msg = null
        code = 0
    }

    private fun loadFromNetwork() {
        resetStatus()
        observeCacheSource()

        val remoteSource = createRequest()
        liveData.addSource(remoteSource) { response ->
            liveData.removeSource(remoteSource)
            if (response.isSuccessful()) {
                status = Status.SUCCESS
                msg = response.message

                saveResponseAnReInit(response)
            } else {
                handleFailed(response.result, response.message)
            }
        }
    }

    private fun observeCacheSource() {
        //此处为正式监听本地数据，后续不再observe CacheResource
        //因为remove了之后再次observe，会先查询一次，再去check 数据的invalid状态，如果数据处于invalid状态会再次查询，导致回调两次
        liveData.removeSource(cacheSource)
        liveData.addSource(cacheSource) {
            liveData.value = Resource(data = it, msg = msg, status = status)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun saveResponseAnReInit(response: ApiResponse<A>) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                response.data?.also {
                    saveApiResponse(it)
                    if (it is ListData && (it as ListData?)?.getListDataSize() ?: 0 <= 0) {
                        uiThread {
                            observeCacheSource()
                        }
                    }
                }

                return null
            }

        }.executeOnExecutor(ThreadExecutorFactory.getDbWritableExecutor())
    }

    //记得在调用时维护最新状态
    private fun updateStatus() {
        val res = liveData.value
        liveData.value = Resource(code = code, msg = msg, status = status, data = res?.data)
    }

    private fun handleFailed(
        code: Int,
        msg: String?
    ) {
        onFailed(code, msg)
        this.status = Status.FAILED
        this.code = code
        this.msg = msg

        updateStatus()

        //保证error只触发一次，否则数据库的改动都会导致error
        resetStatus()
        this.status = Status.SUCCESS
        //把数据库数据当做success
        observeCacheSource()

    }

    override fun asLiveData(): LiveData<Resource<R>> {
        if (!initialed) {
            init()
            initialed = true
        }
        return liveData
    }

    override fun shouldRequest(resource: R): Boolean {
        return true
    }


}