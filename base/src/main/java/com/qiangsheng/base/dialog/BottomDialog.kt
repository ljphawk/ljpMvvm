package com.qiangsheng.base.dialog

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup

/*
 *@创建者       L_jp
 *@创建时间     2019/9/6 15:51.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
class BottomDialog {
    class Builder(context: Context, layoutId: Int) :
        BaseDialog.Builder<Builder>(context) {
        init {
            setContentView(layoutId)
            //必须全屏
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            //设置为没有蒙层
//            setBackgroundDimEnabled(false)
            setGravity(Gravity.BOTTOM)
            setCancelable(true)
        }
    }
}