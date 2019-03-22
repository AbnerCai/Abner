package top.abner.base.thread

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/21 9:31
 */
class AppExecutors {

    private val THREAD_COUNT = 5

    private val diskIO: Executor

    private val networkIO: Executor

    private val mainThread: Executor

    init {
        this.diskIO = DiskIOThreadExecutor()
        this.networkIO = Executors.newFixedThreadPool(THREAD_COUNT)
        this.mainThread = MainThreadExecutor()
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(@NonNull command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}