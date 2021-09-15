package com.qiangsheng.base.dialog

import android.content.Context
import android.view.Gravity

/*
 *@创建者       L_jp
 *@创建时间     2019/9/6 15:51.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
class NomalDialog {
    class Builder(context: Context) :
        BaseDialog.Builder<Builder>(context) {
        init {
            setGravity(Gravity.CENTER)
            setCancelable(true)
        }
    }
}