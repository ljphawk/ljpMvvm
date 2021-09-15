package com.zhixing.zuche.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.zhixing.zuche.databinding.ActivityMainBinding
import com.zhixing.zuche.ui.base.binding.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private val mainVm by viewModels<MainViewModel>()

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        setSwipeBackEnable(false)

    }


}