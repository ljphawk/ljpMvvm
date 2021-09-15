package com.qiangsheng.respository.api

import androidx.lifecycle.LiveData
import com.qiangsheng.respository.db.model.YmjzInfo
import com.qiangsheng.respository.model.HomeBannerItemBean
import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.requestbody.BaseRequestBody
import com.qiangsheng.respository.requestbody.NomalRequestData
import retrofit2.http.Body
import retrofit2.http.POST


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:22.
 *@描述
 */
interface TestApiService {

    @POST("service-order-customer/customer/event/home_events")
    fun getHomeBannerAdList(@Body body: BaseRequestBody<NomalRequestData>): LiveData<ApiResponse<MutableList<HomeBannerItemBean>>>

    @POST("meeting/batchUpdate")
    suspend fun uploadInfo(@Body body: BaseRequestBody<NomalRequestData>): ApiResponse<Any>

}