package com.qiangsheng.respository.app

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2020/7/19 10:44.
 *@描述
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this);
        MMKV.initialize(this);

    }

}