package com.zhixing.zuche.ui.activity.splash

import android.os.Bundle
import com.zhixing.zuche.R
import com.zhixing.zuche.databinding.ActivitySplashBinding
import com.zhixing.zuche.ui.activity.main.MainActivity
import com.zhixing.zuche.ui.base.binding.BaseBindingActivity


/*
 *@创建者       L_jp
 *@创建时间     2020/11/3 2:06 PM.
 *@描述
 */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    override fun getContentView(): Int {
        return R.layout.activity_splash
    }

    override fun initData(savedInstanceState: Bundle?) {
        setSwipeBackEnable(false)
//        if (UserUtils.isLogin()) {
//            MainActivity.startActivity(this)
//        } else {
//            LoginActivity.startActivity(this)
//        }
        startActivity(MainActivity::class.java)
        finish()
    }
}