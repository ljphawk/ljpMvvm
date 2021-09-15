package com.zhixing.zuche.ui.base.my

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.qiangsheng.base.utils.CommUtils
import com.qiangsheng.titlebar.TitleBar
import com.qiangsheng.titlebar.listener.OnLeftClickListener
import com.zhixing.zuche.R
import com.zhixing.zuche.extensions.removeCallbacksDismissAllDialog
import com.zhixing.zuche.manager.ActivityManager
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/*
 *@创建者       L_jp
 *@创建时间     2020/5/4 16:19.
 *@描述
 *
 *@更新者         $
 *@更新时间         $
 *@更新描述
 */
abstract class MyActivity : SwipeBackActivity(), OnLeftClickListener {

    /**
     * 子类使用tag
     */
    protected var TAG: String = this::class.java.simpleName

    protected lateinit var immersionBar: ImmersionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getContentView() > 0) {
            setContentView(getContentView())
        }
        ActivityManager.addActivity(this)

        initTitleBar()
        initImmersionBar()
        //数据初始化
        initData(savedInstanceState)
    }

    /**
     * 添加当前布局
     */
    protected abstract fun getContentView(): Int

    /**
     *初始化数据,设置数据
     */
    protected abstract fun initData(savedInstanceState: Bundle?)

    protected open fun initImmersionBar() {
        //默认的沉浸式方式
        immersionBar = ImmersionBar.with(this)
            .transparentStatusBar()
            .navigationBarColor(R.color.white)
            .statusBarDarkFont(true, 0.2f)
            .navigationBarDarkIcon(true, 0.2f)
        val view = immersionBarView()
        if (view != null) {
            viewImmersionBar(view)
        } else {
            defaultImmersionBar()
        }
    }


    /**
     * 沉浸状态的viewId
     */
    protected open fun immersionBarView(): View? {
        return null
    }

    /**
     * 默认的沉浸式方式
     */
    private fun defaultImmersionBar() {
        immersionBar
            .statusBarColor(R.color.white)
            .fitsSystemWindows(true)
            .init()
    }

    /**
     * 使一个view延伸至状态栏
     */
    private fun viewImmersionBar(view: View) {
        immersionBar
            .fitsSystemWindows(false)
            .titleBar(view)
            .init()
    }

    protected open fun initTitleBar() {
        getTitleBar()?.setLeftOnClickListener(this)
    }


    fun getTitleBar(): TitleBar? {
        val titleBar = findViewById(R.id.base_toolbar)
        return if (titleBar != null && titleBar is TitleBar) titleBar else null
    }

    override fun onLeftClick(v: View?) {
        titleBarLeftClick()
    }

    /**
     * 关闭activity前的操作  ->
     * 如果需要自己控制 返回的操作，重写该方法， 删除super
     */
    protected open fun titleBarLeftClick() {
        finish()
    }

    override fun finish() {
        CommUtils.hideSoftKeyBoard(this)
        super.finish()
    }

    override fun onDestroy() {
        removeCallbacksDismissAllDialog()
        ActivityManager.removeActivity(this)
        super.onDestroy()
    }

}