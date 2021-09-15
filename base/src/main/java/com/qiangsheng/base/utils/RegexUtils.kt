package com.qiangsheng.base.utils

import android.text.TextUtils
import java.util.regex.Pattern


/*
 *@创建者       L_jp
 *@创建时间     2020/7/22 9:18.
 *@描述         
 */
object RegexUtils {

    /**
     * 验证手机格式
     */
    fun isMobileNO(mobiles: String): Boolean {
        /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        val telRegex = "[1][\\d]\\d{9}"
        return check(mobiles, telRegex)
    }


    private fun check(str: String?, regex: String?): Boolean {
        return try {
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(str)
            matcher.matches()
        } catch (e: Exception) {
            false
        }
    }
}