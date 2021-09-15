package com.zhixing.zuche.data

import androidx.annotation.DrawableRes


/*
 *@创建者       L_jp
 *@创建时间     2020/11/4 10:54 AM.
 *@描述
 */
data class MainListDataBean(
    val name: String,
    @DrawableRes val icon: Int,
    val type: Int
){
    companion object{
        val TYPE_IDENTIFY  =0
        val TYPE_SPOT_REPORT  =1
        val TYPE_CALL_TAXI  =2
    }
}