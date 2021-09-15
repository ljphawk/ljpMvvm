package com.zhixing.zuche.ui.base

import androidx.lifecycle.ViewModel
import com.qiangsheng.respository.app.BaseApplication


/*
 *@创建者       L_jp
 *@创建时间     2020/7/21 21:04.
 *@描述         
 */
open class BaseViewModel : ViewModel() {

    fun getString(resId: Int): String {
        return BaseApplication.instance.getString(resId)
    }
}