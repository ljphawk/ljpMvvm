package com.qiangsheng.respository.network

import androidx.lifecycle.LiveData

interface IResourceLiveData<R> {

    fun asLiveData(): LiveData<Resource<R>>
}