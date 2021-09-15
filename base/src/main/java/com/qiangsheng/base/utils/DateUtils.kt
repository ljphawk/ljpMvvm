package com.qiangsheng.base.utils

import java.text.SimpleDateFormat
import java.util.*


/*
 *@创建者       L_jp
 *@创建时间     2020/4/14 15:49.
 *@描述
 */
object DateUtils {

    const val ONE_SECOND: Long = 1000
    const val ONE_MINUTE = 60 * ONE_SECOND
    const val ONE_HOUR = 60 * ONE_MINUTE
    const val ONE_DAY = 24 * ONE_HOUR
    const val ONE_WEEK = 7 * ONE_DAY
    const val ONE_MONTH = 30 * ONE_DAY

    const val YMDHMS1 = "yyyy-MM-dd HH:mm:ss"
    const val YMDHMS2 = "yyyy-MM-dd"
    const val YMDHMS3 = "yyyy-MM-dd HH:mm"
    const val YMDHMS4 = "MM-dd HH:mm"
    const val YMDHMS5 = "yyyy-MM"

    /**
     * 获取当前的时间戳
     */
    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    /**
     *  @param timeStamp 秒(10位)
     * @param dateFormat $YMDHMS
     * @return 默认返回固定的 yyyy-MM-dd
     */
    fun getDateFormat(
        timeStamp: Long = getCurrentTimestamp(),
        dateFormat: String = YMDHMS2
    ): String {
        return try {
            val sdr = SimpleDateFormat(dateFormat)
            sdr.format(Date(timeStamp * 1000))
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 返回时间戳
     */
    fun getTimestamp(time: String, sdfForm: String): String {
        var times = ""
        try {
            val sdf = SimpleDateFormat(sdfForm)
            val date = sdf.parse(time)
            times = (date.time / 1000).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return times
    }


    /**
     * 时间格式化  秒 转00:00 || 00：00：00
     * @param seconds 秒!
     */
    fun convertSecondToFormatString(seconds: Int): String {
        var min = seconds / 60
        val sec = seconds % 60
        val hour = min / 60
        val timeSb = StringBuffer()
        if (hour > 0) {
            timeSb.append(if (hour >= 10) "" + hour else "0$hour")
            timeSb.append(":")
            min %= 60
        }

        timeSb.append(if (min >= 10) "" + min else "0$min")
        timeSb.append(":")
        timeSb.append(if (sec >= 10) "" + sec else "0$sec")
        return timeSb.toString()
    }


    fun formatElapsedTime(timestamp: Long): String {
        val diffMills = System.currentTimeMillis() - timestamp
        return when {
            diffMills < 20L * ONE_SECOND -> {
                "刚刚 "
            }
            diffMills < ONE_MINUTE -> {
                "${diffMills / ONE_SECOND}秒前 "
            }
            diffMills < ONE_HOUR -> {
                "${diffMills / ONE_MINUTE}分钟前 "
            }
            diffMills < ONE_DAY -> {
                "${diffMills / ONE_HOUR}小时前 "
            }
            diffMills < ONE_MONTH -> {
                "${diffMills / ONE_DAY}天前 "
            }
            isSameYear(timestamp) -> {
                getDateFormat(timestamp, YMDHMS4)
            }
            else -> {
                getDateFormat(timestamp, YMDHMS2)
            }
        }

    }


    private fun isSameYear(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val year = calendar.get(Calendar.YEAR)

        calendar.timeInMillis = System.currentTimeMillis()
        val currentYear = calendar.get(Calendar.YEAR)

        return year == currentYear
    }


    /**
     * 获取当前的时间浩渺值
     */
    fun getCurrentCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

}