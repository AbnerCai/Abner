package top.abner.base.thread

import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * IO 线程
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/21 9:28
 */
class DiskIOThreadExecutor : Executor {

    private val mDiskIO: Executor

    init {
        mDiskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(@NonNull command: Runnable) {
        mDiskIO.execute(command)
    }
}