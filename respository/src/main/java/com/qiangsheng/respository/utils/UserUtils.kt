package com.qiangsheng.respository.utils


/*
 *@创建者       L_jp
 *@创建时间     2020/11/3 2:53 PM.
 *@描述
 */
object UserUtils {

    private var loginSuccess = false

    fun isLogin(): Boolean {
        return loginSuccess
    }

    fun login() {
        loginSuccess = true
    }
}