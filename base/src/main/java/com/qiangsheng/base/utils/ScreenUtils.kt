package com.qiangsheng.base.utils

import android.content.Context
import android.util.DisplayMetrics

object ScreenUtils {
    /**
     * screenWidth
     * @param context
     * @return
     */
    fun getScreenWidth(
        context: Context,
        displayMetrics: DisplayMetrics = getDisplayMetrics(
            context
        )
    ): Int {
        return displayMetrics.widthPixels
    }

    /**
     * screenHeight
     * @param context
     * @return
     */
    fun getScreenHeight(
        context: Context,
        displayMetrics: DisplayMetrics = getDisplayMetrics(
            context
        )
    ): Int {
        return displayMetrics.heightPixels
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    /**
     * screenWidth 的dp
     * @param context
     * @return
     */
    fun getScreenWidthDp(context: Context): Float {
        val dm = getDisplayMetrics(context)
        val density: Float = dm.density
        val width = getScreenWidth(context, dm)
        return (width / density)
    }

    /**
     * screenHeight 的dp
     * @param context
     * @return
     */
    fun getScreenHeightDp(context: Context): Float {
        val dm = getDisplayMetrics(context)
        val density: Float = dm.density
        val height = getScreenHeight(context, dm)
        return (height / density)
    }


}