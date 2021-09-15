package com.qiangsheng.respository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:23.
 *@描述
 */
@Parcelize
data class HomeBannerItemBean(
    val image_url: String?,
    val jump_title: String?,
    val jump_url: String?
):Parcelable