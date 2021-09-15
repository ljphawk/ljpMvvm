package com.zhixing.zuche.application

import android.content.Context
import android.os.Build
import android.webkit.WebView
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.qiangsheng.respository.app.BaseApplication
import com.qiangsheng.respository.network.addNetworkInterceptors
import com.zhixing.zuche.BuildConfig
import com.zhixing.zuche.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.Component
import dagger.hilt.android.HiltAndroidApp


/*
 *@创建者       L_jp
 *@创建时间     2020/9/24 11:42 AM.
 *@描述
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        webViewSetPath()
        //Fresco
        initFresco()
        //Stetho
        initStetho()
        //smartRefresh
        initSmartRefresh()
    }

    /**
     * 28+的webView行为变更
     */
    private fun webViewSetPath() {
        //https://developer.android.com/about/versions/pie/android-9.0-changes-28?hl=zh-cn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName()
            if (packageName != processName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    private fun initSmartRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white) //全局设置主题颜色
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            val footer = ClassicsFooter(context)
            footer.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            footer
        }
    }



    private fun initFresco() {
        Fresco.initialize(instance)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
        if (BuildConfig.DEBUG) {
            addNetworkInterceptors(StethoInterceptor())
        }
    }
}