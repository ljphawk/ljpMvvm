package com.qiangsheng.base.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.qiangsheng.base.R


/*
 *@创建者       L_jp
 *@创建时间     2020/5/19 14:36.
 *@描述               
 */

object PackageUtils {
    /**
     * 获取版本名称 1.1.1
     */
    fun getVersionName(context: Context): String? {

        //获取包管理器
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo =
                pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取版本号 123
     */
    fun getVersionCode(context: Context): Long {
        val pm = context.packageManager
        try {
            val packageInfo =
                pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0L
    }

    /**
     * 获取App的名称
     */
    fun getAppName(context: Context): String {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo =
                pm.getPackageInfo(context.packageName, 0)
            //获取应用 信息
            val applicationInfo = packageInfo.applicationInfo
            //获取albelRes
            val labelRes = applicationInfo.labelRes
            //返回App的名称
            return context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}