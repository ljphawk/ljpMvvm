package com.zhixing.zuche.ui.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.zhixing.zuche.extensions.inflateBindingWithGeneric
import com.zhixing.zuche.ui.base.BaseFragment


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:47 AM.
 *@描述
 */
abstract class BaseBindingFragment<VB : ViewBinding> : BaseFragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun getContentLayoutId(): Int {
        return -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBindingWithGeneric(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
