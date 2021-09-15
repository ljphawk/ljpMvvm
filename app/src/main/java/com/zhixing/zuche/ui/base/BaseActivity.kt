package com.zhixing.zuche.ui.base

import android.content.Intent
import com.zhixing.zuche.ui.base.my.MyActivity


abstract class BaseActivity : MyActivity() {

    fun startActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }


}