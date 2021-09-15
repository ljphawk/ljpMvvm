package com.zhixing.zuche.ui.base.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.components.ImmersionFragment
import com.gyf.immersionbar.components.ImmersionOwner
import com.qiangsheng.base.utils.CommUtils
import com.zhixing.zuche.extensions.removeCallbacksDismissAllDialog

/*
 *@创建者       L_jp
 *@创建时间     2020/5/4 15:39.
 *@描述
 *
 *@更新者         $
 *@更新时间         $
 *@更新描述
 */
abstract class MyFragment : ImmersionFragment() {


    override fun initImmersionBar() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (getContentLayoutId() > 0) {
            inflater.inflate(getContentLayoutId(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }


    /**
     * layoutId
     */
    abstract fun getContentLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(view, savedInstanceState)
    }

    protected abstract fun initData(view: View, savedInstanceState: Bundle?)

    override fun onDestroyView() {
        removeCallbacksDismissAllDialog()
        requireActivity()?.let {
            CommUtils.hideSoftKeyBoard(it)
        }
        super.onDestroyView()
    }
}