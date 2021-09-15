package com.qiangsheng.respository.mmkv

import com.qiangsheng.respository.BuildConfig
import com.qiangsheng.respository.extensions.*
import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 4:13 PM.
 *@描述
 */

object ConfigPreference : BasePreference() {

    override fun create(): MMKV {
        return MMKV.mmkvWithID("config")!!
    }

    /**
     * app host status
     */
    var APP_HOST_STATUS by stringValue(defaultValue = BuildConfig.BUILD_TYPE)
}
