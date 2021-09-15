package com.qiangsheng.base.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.qiangsheng.base.extensions.uiThread


/*
 *@创建者       L_jp
 *@创建时间     2020/3/19 18:07.
 *@描述        普通toast操作类
 *
 *@更新者         $
 *@更新时间         $
 *@更新描述         
 */

fun Context?.showToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    show(this, message, duration)
}

fun Context?.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    this.showToast(this?.getString(resId), duration)
}

fun Fragment?.showToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    show(this?.context, message, duration)
}

fun Fragment?.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    this.showToast(this?.getString(resId), duration)
}

private var toastCompat: ToastCompat? = null
private fun show(context: Context?, message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (context == null || message.isNullOrEmpty()) {
        return
    }

    uiThread {
        try {
            toastCompat?.cancel()
            toastCompat = null
            toastCompat = ToastCompat.makeText(context.applicationContext, message, duration)
            toastCompat?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}