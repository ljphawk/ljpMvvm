package com.zhixing.zuche.ui.base.binding

import androidx.viewbinding.ViewBinding
import com.zhixing.zuche.extensions.inflateBindingWithGeneric
import com.zhixing.zuche.ui.base.BaseActivity


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:15 AM.
 *@描述
 */
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity() {

    lateinit var binding: VB

    override fun getContentView(): Int {
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        return -1
    }


}