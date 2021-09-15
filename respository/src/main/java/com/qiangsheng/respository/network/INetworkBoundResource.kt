package com.qiangsheng.respository.network

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData

interface INetworkBoundResource<R, A> : INetworkResource<A>, IResourceLiveData<R> {

    /**
     * 存储APi返回的结果，框架已经做了异步处理，不需要自己再实现异步方式了
     * @param response
     */
    fun saveApiResponse(@NonNull response: A)

    fun shouldRequest(@Nullable resource: R): Boolean

    fun loadFromCache(): LiveData<R>
}