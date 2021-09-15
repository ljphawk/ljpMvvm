package com.zhixing.zuche.ui.base.binding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 2:20 PM.
 *@描述
 */
abstract class BindingViewDelegate<T, VB : ViewBinding> (
    private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB
): ItemViewDelegate<T, BindingViewDelegate.BindingViewHolder<VB>>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup) : BindingViewHolder<VB> =
        BindingViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}