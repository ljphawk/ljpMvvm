package com.zhixing.zuche.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

/*
 *@创建者       L_jp
 *@创建时间     2019/9/19 9:36.
 *@描述
 */
abstract class BaseLayout : FrameLayout {

    protected val TAG = this::class.java.simpleName

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @SuppressLint("ResourceType")
    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        isClickable = true
        if (layoutId() > 0) {
            LayoutInflater.from(context).inflate(layoutId(), this)
        }
        val attr = attrId()
        var typedArray: TypedArray? = null
        attr?.let {
            typedArray = context.obtainStyledAttributes(attrs, it)
        }
        initData(typedArray)
    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun attrId(): IntArray?

    protected abstract fun initData(typedArray: TypedArray?)


    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

}