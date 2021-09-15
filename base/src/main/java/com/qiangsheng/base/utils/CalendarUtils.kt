package com.qiangsheng.base.utils

import java.util.*


/*
 *@创建者       L_jp
 *@创建时间     2020/8/6 08:58.
 *@描述
 */
class CalendarUtils {

    private val calendar: Calendar = Calendar.getInstance(Locale.CHINA)

    fun getYear(): Int {
        return calendar.get(Calendar.YEAR)
    }

    fun getMonth(): Int {
        return calendar.get(Calendar.MONTH)
    }

    fun getDayOfMonth(): Int {
        return calendar.get(Calendar.DAY_OF_MONTH)
    }


    fun getDayOfWeek(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getTimeMillis(): Long {
        return calendar.timeInMillis / 1000
    }

    fun getWeeks(): Int {
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getHourOfDay(): Int {
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun setMonth(month: Int) {
        calendar.set(Calendar.MONTH, month)
    }

    fun setDayOfMonth(dayOfMonth:Int){
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
    }
}