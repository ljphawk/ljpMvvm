package com.qiangsheng.respository.network

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.qiangsheng.respository.model.base.ListData

/**
 * 包含分页 包含普通请求 包含重试机制
 * @param failedRetryCount 失败的重试次数,默认0 (表示正常一次请求失败后,会重试的次数)
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
abstract class NetworkResource<R>(private val failedRetryCount: Int = 0) : INetworkResource<R>,
    IResourceLiveData<R> {

    private val liveData = MediatorLiveData<Resource<R>>()
    private var initialed = false

    private var page = 1
    private var pageTrigger = MutableLiveData<Int>()
    private var pagingInfo = PagingInfo(page)
    private var currentRetryCount = 0//当前已经重试的次数

    private fun init() {
        liveData.addSource(pageTrigger) {
            loadRequestData()
        }
        reload()
    }

    private fun loadRequestData() {
        pagingInfo.page = page
        liveData.value = Resource(status = Status.LOADING, paging = pagingInfo)
        val webSource = createRequest()

        liveData.addSource(webSource) { res ->
            liveData.removeSource(webSource)

            val status: Status
            val data: R?
            val hasMoreData: Boolean

            if (res.isSuccessful()) {
                onResponseSuccess(res)
                status = Status.SUCCESS
                data = res.data
                hasMoreData = when (data) {
                    is ListData? -> {
                        //现在只要list有值 就可以继续加载下一页
                        data?.getListDataSize() ?: 0 > 0
                    }
                    is Collection<*>? -> {
                        //现在只要list有值 就可以继续加载下一页
                        data?.size ?: 0 > 0
                    }
                    else -> {
                        true
                    }
                }
            } else {
                status = Status.FAILED
                onFailed(res.result, res.message)
                data = null
                hasMoreData = true
            }
            pagingInfo.page = page
            pagingInfo.hasMoreData = hasMoreData
            pagingInfo.loadFailRetry = status == Status.FAILED

            //失败了 去重试
            if (status == Status.FAILED && currentRetryCount < failedRetryCount) {
                currentRetryCount++
                thePageTrigger()
                return@addSource
            }

            currentRetryCount = 0
            liveData.value = Resource(
                code = res.result,
                msg = res.message,
                data = data,
                status = status,
                paging = pagingInfo
            )
            //失败了,页码复原 (一定要在 $liveData 改变后执行)
            if (pagingInfo.loadFailRetry && page > 1) {
                page--
            }
        }
    }

    /**
     * 从第一页开始加载
     */
    fun reload() {
        page = 1
        thePageTrigger()
    }

    /**
     * 加载下一页
     */
    fun loadNextPage() {
        page += 1
        thePageTrigger()
    }

    private fun thePageTrigger() {
        pageTrigger.value = page
    }

    override fun asLiveData(): LiveData<Resource<R>> {
        if (!initialed) {
            init()
            initialed = true
        }
        return liveData
    }

    /**
     * 分页的数量
     */
    open fun getLimit(): Int {
        return 20
    }

    fun getPage(): Int {
        return page
    }
}