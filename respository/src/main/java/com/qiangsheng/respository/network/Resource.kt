package com.qiangsheng.respository.network

data class Resource<T>(
    var code: Int = 0,
    var msg: String? = null,
    var data: T? = null,
    var status: Status,
    var paging: PagingInfo? = null
)
/**
 * 分页相关的数据
 */
data class PagingInfo(
    var page: Int,//第几页
    var hasMoreData: Boolean = true,//是否还有更多数据
    var loadFailRetry: Boolean = false//加载失败 重试的
) {
    fun isFirstPage(): Boolean {
        return page == 1
    }
}