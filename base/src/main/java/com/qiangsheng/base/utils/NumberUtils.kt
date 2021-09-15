package com.qiangsheng.base.utils

import com.qiangsheng.base.extensions.contentHasValue
import java.math.BigDecimal
import java.math.RoundingMode

object NumberUtils {

    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number   要四舍五入的数字
     * @param decimal  保留的小数点数
     * @param rounding 是否四舍五入
     */
    fun formatNumber(number: Double, decimal: Int = 2, rounding: Boolean = true): Double {
        val bigDecimal = BigDecimal(number)
        return if (rounding) {
            bigDecimal.setScale(decimal, RoundingMode.HALF_UP).toDouble()
        } else {
            bigDecimal.setScale(decimal, RoundingMode.DOWN).toDouble()
        }
    }

    /**
     * 将数字转化成百，千，万显示。
     *
     * @param wordCount
     * @return
     */
    fun formatWordCount(wordCount: Int): String {
        return if (wordCount / 10000 > 0) {
            "${wordCount / 10000}万"
        } else if (wordCount / 1000 > 0) {
            "${wordCount / 1000}千"
        } else if (wordCount / 100 > 0) {
            "${wordCount / 100}百"
        } else {
            wordCount.toString()
        }
    }

    fun phoneToHideMiddle(phone: String?): String? {
        if (!phone.contentHasValue()) {
            return phone
        }
        return if (phone?.length == 11) {
            phone.substring(0, 3) + "****" + phone.substring(7, phone.length)
        } else {
            phone
        }
    }
}