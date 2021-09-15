package com.zhixing.zuche.ui.activity.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.qiangsheng.base.utils.LOG
import com.qiangsheng.respository.extensions.toJsonString
import com.qiangsheng.respository.model.base.ApiResponse
import com.qiangsheng.respository.repo.TestRepository
import com.zhixing.zuche.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 11:14 AM.
 *@描述
 */
@HiltViewModel
class TestViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {

    private val testTrigger = MutableLiveData<Boolean>()

    fun getTestData() {
        testTrigger.value = true
    }

    val getTestData = Transformations.switchMap(testTrigger) {
        testRepository.getHomeBannerListData()
    }

    suspend fun requestUploadInfo(): ApiResponse<Any> {
        return testRepository.uploadInfo()
    }

    fun uploadInfo() {
        viewModelScope.launch {
            val res = testRepository.uploadInfo()
            LOG.d("===data11", toJsonString(res))
            if (res.isSuccessful()) {
                //成功了
            } else {
                //失败了
            }

        }
    }
}