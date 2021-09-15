package com.qiangsheng.base.extensions

import java.util.*


/*
 *@创建者       L_jp
 *@创建时间     2020/4/17 14:19.
 *@描述         
 *
 *@更新者         $
 *@更新时间         $
 *@更新描述         
 */

/**
 * 判断list是否有内容
 */
fun <T> Collection<T>?.hasContent(): Boolean {
    return !isNullOrEmpty()
}


/**
 * 比较两个list是否相等
 */
fun <T> Collection<T>?.compareList(list: Collection<T>): Boolean {
    return this != null && this.size == list.size && this.containsAll(list)
}