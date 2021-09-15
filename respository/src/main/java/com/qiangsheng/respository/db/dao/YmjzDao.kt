package com.qiangsheng.respository.db.dao

import android.content.ContentValues
import com.qiangsheng.respository.db.model.YmjzInfo
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     1/21/21 4:21 PM.
 *@描述
 */
object YmjzDao {

    fun save(data: YmjzInfo?) {
        data?.let {
            it.save()
        }
    }

    fun queryAll(): MutableList<YmjzInfo> {
        return LitePal.findAll(YmjzInfo::class.java)
    }

    fun queryUser(idNo: String): YmjzInfo? {
        return LitePal.where("idNo =?", idNo).findFirst(YmjzInfo::class.java)
    }


    fun updateUploadInfo(data: YmjzInfo) {
        data.isUpload = true
        data.save()
    }

    /**
     * 分页
     */
    fun queryPageData(page: Int, pageSize: Int = 50): MutableList<YmjzInfo> {
        return LitePal.order("id")
            .offset((page - 1) * pageSize)
            .limit(pageSize)
            .find(YmjzInfo::class.java)
    }

    /**
     * 分页每次查询20条并上次
     */
    fun queryNotUploadInfo(): MutableList<YmjzInfo> {
        return LitePal.order("id")
            .where("isUpload =?", "0")
            .limit(20)
            .find(YmjzInfo::class.java)
    }
}