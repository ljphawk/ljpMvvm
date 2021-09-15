package com.qiangsheng.base.extensions


/*
 *@创建者       L_jp
 *@创建时间     2020/3/23 11:55.
 *@描述
 */


/**
 * 内容是否为null或是否为空,或是否只有空格字符串
 */
fun CharSequence?.contentHasValue() =
    !(isNullOrBlank() || isNullOrEmpty())

/**
 * 内容是否为null或是否为空,或是否只有空格字符串
 */
fun allContentHasValue(vararg content: String): Boolean {
    for (item in content) {
        if (!item.contentHasValue()) {
            return false
        }
    }
    return true
}
