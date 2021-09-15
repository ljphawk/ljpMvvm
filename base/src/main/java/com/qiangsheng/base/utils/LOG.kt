package com.qiangsheng.base.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.qiangsheng.base.BuildConfig

object LOG {
    init {
        val logFormat = PrettyFormatStrategy.newBuilder()
            .methodCount(0)
            .methodOffset(5)
            .tag("qsLog")
            .showThreadInfo(false)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(logFormat) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {//是否开启日志
                return BuildConfig.DEBUG
            }
        })
    }

    @JvmStatic
    fun v(message: String) {
        Logger.v(message)
    }

    @JvmStatic
    fun v(tag: String?, message: String) {
        Logger.t(tag).v(message)
    }

    @JvmStatic
    fun i(message: String) {
        Logger.i(message)
    }

    @JvmStatic
    fun i(tag: String?, message: String) {
        Logger.t(tag).i(message)
    }

    @JvmStatic
    fun d(message: String) {
        Logger.d(message)
    }

    @JvmStatic
    fun d(tag: String?, message: String) {
        Logger.t(tag).d(message)
    }

    @JvmStatic
    fun d(obj: Any?) {
        Logger.d(obj)
    }

    @JvmStatic
    fun d(tag: String?, obj: Any?) {
        Logger.t(tag).d(obj)
    }

    /**
     * warning
     */
    @JvmStatic
    fun w(message: String) {
        Logger.w(message)
    }

    @JvmStatic
    fun w(tag: String?, message: String) {
        Logger.t(tag).w(message)
    }

    @JvmStatic
    fun e(message: String) {
        Logger.e(message)
    }

    @JvmStatic
    fun e(tag: String?, message: String) {
        Logger.t(tag).e(message)
    }

    @JvmStatic
    fun e(message: String, throwable: Throwable?) {
        Logger.e(throwable, message)
    }

    @JvmStatic
    fun e(tag: String?, message: String, throwable: Throwable?) {
        Logger.t(tag).e(throwable, message)
    }

    @JvmStatic
    fun json(json: String?) {
        Logger.json(json)
    }

    @JvmStatic
    fun json(tag: String?, json: String?) {
        Logger.t(tag).json(json)
    }

    @JvmStatic
    fun xml(xml: String?) {
        Logger.xml(xml)
    }

}