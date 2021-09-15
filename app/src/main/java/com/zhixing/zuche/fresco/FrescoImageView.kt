package com.zhixing.zuche.fresco

import android.content.Context
import android.util.AttributeSet
import com.facebook.drawee.view.SimpleDraweeView


/*
 *@创建者       L_jp
 *@创建时间     2020/7/20 10:51.
 *@描述
 */
class FrescoImageView  : SimpleDraweeView {

    constructor(context: Context) : super(context) {}

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

}