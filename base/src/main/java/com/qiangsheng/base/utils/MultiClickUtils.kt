package com.qiangsheng.base.utils


/*
 *@创建者       L_jp
 *@创建时间     2020/5/9 18:43.
 *@描述        避免重复快速点击
 */
object MultiClickUtils {

    private var lastClickTime: Long = 0

    fun isFastClick(delayTime: Long = 1000): Boolean {
        var flag = false
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - lastClickTime >= delayTime) {
            flag = true
        }
        lastClickTime = curClickTime
        return flag
    }
}