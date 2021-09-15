package com.qiangsheng.respository.commonkey

import androidx.annotation.StringDef
import com.qiangsheng.respository.BuildConfig
import com.qiangsheng.respository.mmkv.ConfigPreference


/*
 *@创建者       L_jp
 *@创建时间     2020/8/6 14:40.
 *@描述
 */

object AppBaseHost {

    @StringDef(value = [Status.RELEASE, Status.PREV, Status.DEBUG])
    annotation class AppHostStatus


    object Status {
        const val RELEASE = "release"
        const val PREV = "prev"
        const val DEBUG = "debug"
        const val DEBUG_OUTER = "debug_outer"
    }

    object Api {
        const val RELEASE = "http://api.zx62580.com/"
        const val PREV = "http://47.101.56.191:8000/"
        const val DEBUG = "http://172.16.211.10:8000/"//内网
        const val DEBUG_OUTER = "http://58.33.58.115:8000/"//外网
    }

    object Web {
        const val RELEASE = "http://page.zx62580.com/qiangshengH5/#/"
        const val PREV = "http://101.133.133.136:8080/qiangshengH5/#/"
        const val DEBUG = "http://172.16.211.10:8007/qiangshengH5/#/"
    }


    fun getApiHost(): String {
        return if (!BuildConfig.DEBUG) {
            Api.RELEASE
        } else {
            when (ConfigPreference.APP_HOST_STATUS) {
                Status.RELEASE -> Api.RELEASE
                Status.PREV -> Api.PREV
                Status.DEBUG -> Api.DEBUG
                Status.DEBUG_OUTER -> Api.DEBUG_OUTER
                else -> Api.RELEASE
            }
        }
    }

    fun getWebHost(): String {
        return if (!BuildConfig.DEBUG) {
            Web.RELEASE
        } else {
            when (ConfigPreference.APP_HOST_STATUS) {
                Status.RELEASE -> Web.RELEASE
                Status.PREV -> Web.PREV
                Status.DEBUG, Status.DEBUG_OUTER -> Web.DEBUG
                else -> Web.RELEASE
            }
        }
    }

}