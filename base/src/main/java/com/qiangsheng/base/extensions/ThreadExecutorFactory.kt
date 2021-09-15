package com.qiangsheng.base.extensions

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

object ThreadExecutorFactory {
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4))
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
    private const val KEEP_ALIVE_SECONDS = 30L

    private val ioThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "NearIOThread #${mCount.getAndIncrement()}")
        }
    }

    private val networkFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "NearNetworkThread #${mCount.getAndIncrement()}")
        }
    }

    private val backgroundThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            val thread = Thread(r, "NearBackgroundThread #${mCount.getAndIncrement()}")
            thread.priority = Thread.MIN_PRIORITY
            return thread
        }
    }

    private val uploadThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "NearUploadThread #${mCount.getAndIncrement()}")
        }
    }


    private fun createExecutor(threadFactory: ThreadFactory,
                               corePoolSize: Int = CORE_POOL_SIZE,
                               maxPoolSize: Int = MAXIMUM_POOL_SIZE,
                               keepAliveSeconds: Long = KEEP_ALIVE_SECONDS,
                               queueSize: Int = 256): ExecutorService {
        return ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                LinkedBlockingQueue<Runnable>(queueSize),
                threadFactory)
    }

    /**
     * 由于数据库的写操作会锁数据库，因此多个线程不但没有意义，而且还能导致不能看
     */
    private var databaseWriteExecutor: ExecutorService = Executors.newSingleThreadExecutor(ioThreadFactory)

    fun getDbWritableExecutor(): ExecutorService {
        if (databaseWriteExecutor.isShutdown) {
            databaseWriteExecutor = Executors.newSingleThreadExecutor(ioThreadFactory)
        }
        return databaseWriteExecutor
    }

    private var networkExecutor = createExecutor(networkFactory, 0, 24)
    fun getNetworkExecutor(): ExecutorService {
        if (networkExecutor.isShutdown) {
            networkExecutor = createExecutor(networkFactory, 0, 24)
        }
        return networkExecutor
    }

    private var backgroundExecutor = createExecutor(backgroundThreadFactory, 0, 10, queueSize = Int.MAX_VALUE)

    fun getBackgroundExecutor(): ExecutorService {
        if (backgroundExecutor.isShutdown) {
            backgroundExecutor = createExecutor(backgroundThreadFactory, 0, 10, queueSize = Int.MAX_VALUE)
        }
        return backgroundExecutor
    }

    private var uploadExecutor = createExecutor(backgroundThreadFactory, 0, 10, queueSize = Int.MAX_VALUE)

    fun getUploadExecutor(): ExecutorService {
        if (uploadExecutor.isShutdown) {
            uploadExecutor = createExecutor(backgroundThreadFactory, 0, 10, queueSize = Int.MAX_VALUE)
        }
        return uploadExecutor
    }
}