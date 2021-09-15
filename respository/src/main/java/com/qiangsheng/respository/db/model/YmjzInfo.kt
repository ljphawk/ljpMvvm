package com.qiangsheng.respository.db.model

import com.qiangsheng.base.utils.DateUtils
import org.litepal.crud.LitePalSupport


/*
 *@创建者       L_jp
 *@创建时间     1/21/21 4:08 PM.
 *@描述
 */
class YmjzInfo(

) : LitePalSupport() {
    var name: String? = ""
    var idNo: String? = ""//身份证号
    var serviceNo: String? = ""//服务卡号
    var phone: String? = ""
    var meetingType: Int = 0
    var signTimestamp: Long = DateUtils.getCurrentTimestamp()
    var signTime: String = DateUtils.getDateFormat(signTimestamp, DateUtils.YMDHMS3)
    var isUpload: Boolean = false//是否已经上传成功
    var id: Long = 0

}