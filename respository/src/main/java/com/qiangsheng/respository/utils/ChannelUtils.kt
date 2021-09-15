package com.qiangsheng.respository.utils

import com.mcxiaoke.packer.helper.PackerNg
import com.qiangsheng.respository.app.BaseApplication


/*
 *@创建者       L_jp
 *@创建时间     2020/7/21 15:39.
 *@描述         
 */
object ChannelUtils {
    fun getChannel(): String {
        val channel = PackerNg.getChannel(BaseApplication.instance)
        return if (channel.isNullOrBlank() || channel.isNullOrEmpty()) {
            "unKnow"
        } else {
            channel
        }
    }
}