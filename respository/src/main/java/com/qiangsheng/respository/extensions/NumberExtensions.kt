package com.qiangsheng.respository.extensions

import com.qiangsheng.base.extensions.contentHasValue


/*
 *@创建者       L_jp
 *@创建时间     2020/9/9 3:22 PM.
 *@描述
 */


fun String?.toDouble2(default: Double = 0.0): Double {
    return if (!this.contentHasValue()) {
        default
    } else {
        try {
            this?.toDouble() ?: default
        } catch (e: Exception) {
            default
        }

    }
}