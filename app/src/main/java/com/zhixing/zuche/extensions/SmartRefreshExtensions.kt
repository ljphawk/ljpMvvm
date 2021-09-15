package com.zhixing.zuche.extensions

import com.qiangsheng.respository.network.PagingInfo
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/*
 *@创建者       L_jp
 *@创建时间     2020/8/19 5:51 PM.
 *@描述
 */

/**
 * 改变刷新和加载的状态修改
 */
fun SmartRefreshLayout?.changeRefreshStatus(pagingInfo: PagingInfo?) {
    if (pagingInfo == null) {
        this?.finishRefresh()
        this?.finishLoadMore()
        return
    }
    if (pagingInfo.isFirstPage()) {
        if (!pagingInfo.hasMoreData) {
            this?.finishRefreshWithNoMoreData()
        } else {
            this?.finishRefresh(!pagingInfo.loadFailRetry)
        }
    } else {
        //加载失败
        if (pagingInfo.loadFailRetry) {
            this?.finishLoadMore(false)
            return
        }
        //加载成功 ->还有数据加载
        if (pagingInfo.hasMoreData) {
            this?.finishLoadMore()
        } else {
            this?.finishLoadMoreWithNoMoreData()
        }
    }
}