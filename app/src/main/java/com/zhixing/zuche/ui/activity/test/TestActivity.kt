package com.zhixing.zuche.ui.activity.test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import com.permissionx.guolindev.PermissionX
import com.qiangsheng.base.utils.DateUtils
import com.qiangsheng.base.utils.showToast
import com.qiangsheng.respository.mmkv.ConfigPreference
import com.qiangsheng.respository.mmkv.DefaultPreference
import com.zhixing.zuche.databinding.ActivityTestBinding
import com.zhixing.zuche.extensions.createObserver
import com.zhixing.zuche.ui.base.binding.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 11:13 AM.
 *@描述
 */
@AndroidEntryPoint
class TestActivity : BaseBindingActivity<ActivityTestBinding>() {

    private val testVm by viewModels<TestViewModel>()

    override fun initData(savedInstanceState: Bundle?) {
        testVm.getTestData.observe(this, createObserver(onSuccess = {
            showToast("成功了${DateUtils.getCurrentCurrentTimeMillis()}")
        }, onFailed = {
            showToast("失败了${DateUtils.getCurrentCurrentTimeMillis()}")
        }))


//        binding.btGet.setOnClickListener {
        //请求1
//            testVm.getTestData()

        //请求2
//            lifecycleScope.launch {
//                val res =testVm.requestUploadInfo()
//                if (res.isSuccessful()) {
//                    showToast("成功了${DateUtils.getCurrentCurrentTimeMillis()}")
//                } else {
//                    showToast("失败了${DateUtils.getCurrentCurrentTimeMillis()}")
//                }
//            }
//        }


        binding.btSet.setOnClickListener {
             ConfigPreference.APP_HOST_STATUS
            ConfigPreference.APP_HOST_STATUS!!::class
        }
        binding.btGet.setOnClickListener {

        }
        binding.btUpdata.setOnClickListener {

        }
        binding.btRemove.setOnClickListener {

        }
        binding.btClear.setOnClickListener {

        }
    }

    /**
     * 获取拍照权限
     */
    private fun getCameraPermission() {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList, _ ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "功能需要如下权限",
                    "申请",
                    "取消"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "您需要去应用程序设置当中手动开启权限",
                    "我已明白",
                    "取消"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    toTakePhoto()
                }
            }
    }

    /**
     * 去拍照
     */
    private fun toTakePhoto() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {//手机是否有探头
//            startActivity(YmjzScanCodeActivity::class.java)
        } else {
            showToast("未检测到手机摄像头")
        }
    }

}
