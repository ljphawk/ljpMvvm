package com.qiangsheng.respository.mmkv

import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 4:13 PM.
 *@描述
 */
abstract class BasePreference {


    private val _MMKV by lazy {
        create()
    }

    abstract fun create(): MMKV

    fun clearAll() {
        getMMKV().clearAll()
    }

    fun getMMKV(): MMKV {
        return _MMKV
    }

    fun removeValueForKey(key: String) {
        getMMKV().removeValueForKey(key)
    }
}