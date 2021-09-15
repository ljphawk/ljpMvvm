package com.qiangsheng.respository.network

import java.io.IOException


/*
 *@创建者       L_jp
 *@创建时间     1/25/21 1:12 PM.
 *@描述
 */
class ApiException(val errorCode:Int,val errorMsg:String): IOException()