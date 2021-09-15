package com.qiangsheng.base.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings


/*
 *@创建者       L_jp
 *@创建时间     2020/7/21 15:55.
 *@描述         
 */
object SystemUtils {

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    fun getSystemVersion(): String? {
        return Build.VERSION.RELEASE
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    fun getSystemModel(): String? {
        return Build.MODEL
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    fun getDeviceBrand(): String? {
        return Build.BRAND
    }

    /**
     * 跳转到app设置页面
     */
    fun goAppSetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取语言环境
     */
    private fun getLanguage(context: Context): String? {
        val locale = context.resources.configuration.locale
        return locale.language

    }

    /**
     * 是否是中文
     */
    fun isChineseLanguage(context: Context): Boolean {
        return getLanguage(context)?.endsWith("zh") == true
    }
}