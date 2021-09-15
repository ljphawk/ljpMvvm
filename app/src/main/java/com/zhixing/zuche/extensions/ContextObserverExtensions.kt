package com.zhixing.zuche.extensions

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.qiangsheng.base.dialog.BaseDialog
import com.qiangsheng.base.utils.showToast
import com.qiangsheng.respository.extensions.createLoading
import com.qiangsheng.respository.network.PagingInfo
import com.qiangsheng.respository.network.Resource
import com.qiangsheng.respository.network.Status
import java.lang.Exception


class NetDataObserver<T>(
    private var context: Context,
    private var onSuccess: (Resource<T>) -> Unit,
    private var onLoading: ((Resource<T>) -> Unit)? = null,
    private var onFailed: ((Resource<T>) -> Unit)? = null,
    private var showLoading: ((PagingInfo?) -> Boolean),
    private var loadingText: String = "加载中...",
    private var showFailedToast: Boolean = true
) : Observer<Resource<T>> {

    private var hasShowLoad: Boolean = true
    private var loadingDialog: BaseDialog? = null

    override fun onChanged(res: Resource<T>?) {
        if (res == null) return
        when (res.status) {
            Status.SUCCESS -> {
                if (hasShowLoad) {
                    loadingDialog?.dismiss()
                    loadList.remove(loadingDialog)
                }
                onSuccess(res)
            }
            Status.LOADING -> {
                hasShowLoad = showLoading.invoke(res.paging)
                if (hasShowLoad) {
                    if (loadingDialog == null) {
                        loadingDialog = context.createLoading(loadingText)
                    }
                    if (loadingDialog?.isShowing == false) {
                        loadingDialog?.show()
                        loadList.remove(loadingDialog)
                        loadList.add(loadingDialog)
                    }
                } else {
                    loadingDialog?.dismiss()
                }
                onLoading?.invoke(res)
            }
            Status.FAILED -> {
                if (hasShowLoad) {
                    loadingDialog?.dismiss()
                    loadList.remove(loadingDialog)
                }
                if (showFailedToast) {
                    context.showToast(res.msg)
                }
                onFailed?.invoke(res)
            }
        }
    }
}

private val loadList = mutableListOf<BaseDialog?>()

fun <T> Activity.createObserver(
    onSuccess: (Resource<T>) -> Unit,
    onLoading: ((Resource<T>) -> Unit)? = null,
    onFailed: ((Resource<T>) -> Unit)? = null,
    loadingText: String = "加载中...",
    showLoading: ((PagingInfo?) -> Boolean) = { true },
    showFailedToast: Boolean = true
): Observer<Resource<T>> {
    return NetDataObserver(
        context = this,
        onSuccess = onSuccess,
        onLoading = onLoading,
        onFailed = onFailed,
        loadingText = loadingText,
        showLoading = showLoading,
        showFailedToast = showFailedToast
    )
}


fun <T> Fragment.createObserver(
    onSuccess: (Resource<T>) -> Unit,
    onLoading: ((Resource<T>) -> Unit)? = null,
    onFailed: ((Resource<T>) -> Unit)? = null,
    loadingText: String = "加载中...",
    showLoading: ((PagingInfo?) -> Boolean) = { true },
    showFailedToast: Boolean = true
): Observer<Resource<T>> {
    return NetDataObserver(
        context = this.requireContext(),
        onSuccess = onSuccess,
        onLoading = onLoading,
        onFailed = onFailed,
        loadingText = loadingText,
        showLoading = showLoading,
        showFailedToast = showFailedToast
    )
}


fun removeCallbacksDismissAllDialog() {
    try {
        loadList.forEach { dialog ->
            dialog?.window?.let {
                dialog.dismiss()
            }
        }
        loadList.clear()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}