package com.qiangsheng.base.extensions

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 获取参数
 * 例子 val link by getIntentParam<String>("link")
 */
inline fun <reified T> Activity.getIntentParam(key: String) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> intent.getStringExtra(key)
            Int::class.java, Integer::class.java -> intent.getIntExtra(key, 0)
            Float::class.java -> intent.getFloatExtra(key, 0f)
            Boolean::class.java -> intent.getBooleanExtra(key, false)
            else -> (intent.getParcelableExtra(key) as Parcelable)
        }
        ret as T?
    }

inline fun <reified T> Fragment.getBundleParam(key: String) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> arguments?.getString(key)
            Int::class.java, Integer::class.java -> arguments?.getInt(key)
            Boolean::class.java -> arguments?.getBoolean(key)
            else -> null
        }
        ret as T?
    }



