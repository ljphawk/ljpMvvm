package com.zhixing.zuche.ui.base

abstract class BaseLazyFragment : BaseFragment() {

    private var dataLoaded: Boolean = false

    override fun onResume() {
        super.onResume()
        lazyLoad()
    }


    private fun lazyLoad() {
        if (!dataLoaded) {
            dataLoaded = true
            loadData()
        }
    }


    override fun onDestroy() {
        dataLoaded = false
        super.onDestroy()
    }

    /**
     * 加载数据
     */
    abstract fun loadData()
}