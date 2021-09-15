package com.zhixing.zuche.ui.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zhixing.zuche.extensions.inflateBindingWithGeneric
import com.zhixing.zuche.ui.base.BaseFragment
import com.zhixing.zuche.ui.base.BaseLazyFragment


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:47 AM.
 *@描述
 */
abstract class BaseLazyBindingFragment<VB : ViewBinding> : BaseBindingFragment<VB>() {

    private var dataLoaded: Boolean = false

    override fun onResume() {
        super.onResume()
        lazyLoad()
    }


    private fun lazyLoad() {
        if (!dataLoaded) {
            dataLoaded = true
            loadData()
        }
    }


    override fun onDestroy() {
        dataLoaded = false
        super.onDestroy()
    }

    /**
     * 加载数据
     */
    abstract fun loadData()
}
