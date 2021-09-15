package com.zhixing.zuche.extensions

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView


/*
 *@创建者       L_jp
 *@创建时间     2020/7/20 10:53.
 *@描述        fresco加载图片扩展类
 */

/**
 * 图片相关的参数
 */
data class FrescoParams(
    var cornerRadius: Int = 0,//圆角 dip()
    var roundAsCircle: Boolean = false,//是否是圆角
    //边框颜色 默认透明 (ContextCompat.getColor() \ Color.parseColor() )
    @ColorInt
    var borderColor: Int = Color.parseColor("#ffffffff"),
    var borderWidth: Int = 0,//边框宽度 dip()
    @DrawableRes
    var placeholderId: Int = 0,//占位图
    @DrawableRes
    var errorResId: Int = 0//错误图
)

/**
 * 正常加载网络图片
 */
@SuppressLint("ResourceType")
fun SimpleDraweeView.loadImage(
    url: String?,//图片地址
    params: FrescoParams = FrescoParams()
) {
    params.apply {
        var roundingParams = hierarchy.roundingParams
        if (roundingParams == null) {
            roundingParams = RoundingParams.fromCornersRadius(cornerRadius.toFloat())
        }

        //圆形图片
        roundingParams?.roundAsCircle = roundAsCircle

        //边框 颜色和宽度
        if (borderWidth > 0) {
            roundingParams?.setBorder(borderColor, borderWidth.toFloat())
        }

        //设置相关的参数
        hierarchy.roundingParams = roundingParams

        //占位图
        if (placeholderId > 0) {
            hierarchy.setPlaceholderImage(placeholderId);
        }
        //加载错误的图片
        if (errorResId > 0) {
            hierarchy.setFailureImage(errorResId)
        }
    }

    setImageURI(url)
}

