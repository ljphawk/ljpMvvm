package com.qiangsheng.respository.utils

import android.util.Log
import com.qiangsheng.base.extensions.contentHasValue
import com.qiangsheng.respository.app.BaseApplication
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

/*
 *@创建者       L_jp
 *@创建时间     2018/7/10 11:45.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */
object FileUtils {
    /**
     * /data/user/0/com.zhixing.qiangshengpassager/files
     */
    private val appRootPath = BaseApplication.instance.filesDir

    /**
     * /storage/emulated/0/Android/data/com.zhixing.qiangshengpassager/files
     */
    private val sdAppRootPath = BaseApplication.instance.getExternalFilesDir(null)

    fun isFileAndExists(path: String?): Boolean {
        if (!path.contentHasValue()) {
            return false
        }
        return isFileAndExists(File(path))
    }

    fun isFileAndExists(file: File?): Boolean {
        return file?.exists() == true

    }


    fun deleteFile(file: File?) {
        if (isFileAndExists(file)) {
            file?.delete()
        }
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    fun renameFile(oldPath: String?, newPath: String?): Boolean {
        try {
            val oleFile = File(oldPath)
            val newFile = File(newPath)
            //执行重命名
            return oleFile.renameTo(newFile)
        } catch (e: Exception) {
            Log.d("", "renameFile: ")
        }
        return false
    }

    /**
     *
     * @param path  获取文件去后缀的名字
     * @return
     */
    fun getFileName(path: String): String {
        return if (!isFileAndExists(path)) {
            ""
        } else try {
            val start = path.lastIndexOf("/")
            val end = path.lastIndexOf(".")
            if (start != -1 && end != -1) {
                path.substring(start + 1, end)
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    fun saveDownFile(responseBody: ResponseBody?, savePath: String): String {
        if (responseBody == null) {
            return savePath
        }
        val file = File(savePath)
        if (file.exists()) {
            file.delete()
        }
        val os: FileOutputStream
        try {
            val res = responseBody.byteStream()
            os = FileOutputStream(file)
            var len: Int
            val buff = ByteArray(1024 * 1024)
            while (res.read(buff).also { len = it } != -1) {
                os.write(buff, 0, len)
            }
            os.close()
            res.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return savePath
    }
}