package com.qiangsheng.base.extensions

import java.util.*

/*
 *@创建者       L_jp
 *@创建时间     2020/3/20 11:34.
 *@描述
 */

/**
 * 图片地址是否是git
 */
fun String.imageUrlIsGif(): Boolean {
    return if (this.isBlank() || this.isEmpty()) {
        false
    } else toLowerCase(Locale.ROOT).contains(".gif")

}
