package com.qiangsheng.respository.requestbody

import com.qiangsheng.base.utils.DateUtils
import com.qiangsheng.base.utils.PackageUtils
import com.qiangsheng.base.utils.SystemUtils
import com.qiangsheng.respository.app.BaseApplication
import com.qiangsheng.respository.utils.ChannelUtils


/*
 *@创建者       L_jp
 *@创建时间     2020/7/21 15:07.
 *@描述         
 */

data class BaseRequestBody<T>(private var data: T) {
    private val common: CommonRequestBody = createBaseRequestBodyCommon()

    companion object {
        fun getNomal(): BaseRequestBody<NomalRequestData> {
            return BaseRequestBody(NomalRequestData())
        }
    }
}

class NomalRequestData()

data class CommonRequestBody(
    var channel_name: String? = null,
    var phone_manu_facturer: String?,//手机制造商
    var phone_model: String?,//手机型号
    var phone_version: String?,//系统版本，安卓
    var request_time: String?,//时间戳
    var request_time_str: String?,//2020-05-08 17:00:00
    var version_code: String?,//app版本号
    var version_name: String?//app版本名称
)

fun createBaseRequestBodyCommon(): CommonRequestBody {
    return CommonRequestBody(
        channel_name = ChannelUtils.getChannel(),
        phone_manu_facturer = SystemUtils.getDeviceBrand(),
        phone_model = SystemUtils.getSystemModel(),
        phone_version = SystemUtils.getSystemVersion(),
        request_time = System.currentTimeMillis().toString(),
        request_time_str = DateUtils.getDateFormat(dateFormat = DateUtils.YMDHMS1),
        version_code = PackageUtils.getVersionCode(BaseApplication.instance).toString(),
        version_name = PackageUtils.getVersionName(BaseApplication.instance)
    )
}