package com.zhixing.zuche.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.zhixing.zuche.extensions.inflateBindingWithGeneric

/*
 *@创建者       L_jp
 *@创建时间     2019/9/19 9:36.
 *@描述
 */
abstract class BaseBindingLayout<VB : ViewBinding> : BaseLayout {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun layoutId(): Int {
        _binding = inflateBindingWithGeneric<VB>(LayoutInflater.from(context), this, true)
        return -1
    }

}