package com.zhixing.zuche.ui.base.binding.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhixing.zuche.extensions.inflateBindingWithGeneric


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:53 AM.
 *@描述
 */
abstract class BaseBindingQuickAdapter<T, VB : ViewBinding>(layoutResId: Int = -1) :
    BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>(layoutResId) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
        BaseBindingHolder(inflateBindingWithGeneric<VB>(parent))

    class BaseBindingHolder(private val binding: ViewBinding) : BaseViewHolder(binding.root) {
        constructor(itemView: View) : this(ViewBinding { itemView })

        @Suppress("UNCHECKED_CAST")
        fun <VB : ViewBinding> getViewBinding() = binding as VB
    }
}