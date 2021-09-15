package com.qiangsheng.respository.eventbus

import org.greenrobot.eventbus.EventBus


/*
 *@创建者       L_jp
 *@创建时间     2020/7/27 11:40.
 *@描述         
 */
object AppEvent {
    fun get(): EventBus {
        return EventBus.getDefault()
    }
}