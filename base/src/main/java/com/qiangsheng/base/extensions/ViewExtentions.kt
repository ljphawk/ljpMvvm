package com.qiangsheng.base.extensions

import android.view.View

/**
 * Created by ivan on 08/02/2018.
 */
fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.show(show:Boolean){
    if(show) visible() else gone()
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.isGone(): Boolean = visibility == View.GONE