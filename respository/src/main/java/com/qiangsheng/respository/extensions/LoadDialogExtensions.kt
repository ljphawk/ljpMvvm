package com.qiangsheng.respository.extensions

import android.content.Context
import com.qiangsheng.base.dialog.BaseDialog
import com.qiangsheng.base.dialog.LoadingDialog


/*
 *@创建者       L_jp
 *@创建时间     2020/7/22 11:06.
 *@描述         
 */

/**
 * 显示加载
 */
fun Context.createLoading(content: String): BaseDialog {
    return LoadingDialog.Builder(this).setMessage(content).create()
}
