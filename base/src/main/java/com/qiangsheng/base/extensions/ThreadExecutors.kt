package com.qiangsheng.base.extensions

import android.os.Handler
import android.os.Looper

/**
 * 线程扩展类
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f: () -> Unit) {
    ThreadExecutorFactory.getDbWritableExecutor().execute {
        try {
            f()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun ioThread(runnable: Runnable) {
    ThreadExecutorFactory.getDbWritableExecutor().execute {
        try {
            runnable.run()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun uiThread(f: () -> Unit) {
    if (isMainThread()) {
        f()
    } else {
        MainExecutor.mainHandler.post(f)
    }
}

fun uiThreadDelay(delay: Long, f: () -> Unit) {
    if (delay <= 0) {
        uiThread(f)
    } else {
        MainExecutor.mainHandler.postDelayed(f, delay)
    }
}


fun uiThread(runnable: Runnable) {
    if (isMainThread()) {
        runnable.run()
    } else {
        MainExecutor.mainHandler.post(runnable)
    }
}

fun background(f: () -> Unit) {
    ThreadExecutorFactory.getBackgroundExecutor().execute {
        try {
            f()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun isMainThread(): Boolean = Looper.getMainLooper().thread.id == Thread.currentThread().id

private object MainExecutor {
    val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }
}