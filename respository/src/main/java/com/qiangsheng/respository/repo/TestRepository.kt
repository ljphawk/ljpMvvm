package com.qiangsheng.respository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.qiangsheng.respository.api.TestApiService
import com.qiangsheng.respository.db.model.YmjzInfo
import com.qiangsheng.respository.model.HomeBannerItemBean
import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.network.NetworkResource
import com.qiangsheng.respository.network.Resource
import com.qiangsheng.respository.requestbody.BaseRequestBody
import com.qiangsheng.respository.requestbody.NomalRequestData
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:25.
 *@描述
 */
class TestRepository @Inject constructor(private val testApiService: TestApiService) {

    /**
     * 首页banner广告
     */
    fun getHomeBannerListData(): LiveData<Resource<MutableList<HomeBannerItemBean>>> {
        return object : NetworkResource<MutableList<HomeBannerItemBean>>() {
            override fun createRequest(): LiveData<ApiResponse<MutableList<HomeBannerItemBean>>> {
                return testApiService.getHomeBannerAdList(BaseRequestBody(NomalRequestData()))
            }

        }.asLiveData()
    }

    suspend fun uploadInfo(): ApiResponse<Any> {
        return withContext(Dispatchers.IO) {
            testApiService.uploadInfo(BaseRequestBody(NomalRequestData()))
        }
    }
}