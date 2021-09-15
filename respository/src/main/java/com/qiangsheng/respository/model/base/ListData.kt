package com.qiangsheng.respository.model.base

/**
 * Created by ivan on 2018/1/29.
 * 用于PagedNetworkBoundResource，来过滤observe数据源时，数据还未完全存储到本地
 */
interface ListData {

    fun getListDataSize(): Int
}