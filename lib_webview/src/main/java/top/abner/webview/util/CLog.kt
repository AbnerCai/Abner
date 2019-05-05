package top.abner.webview.util

import android.util.Log

/**
 *
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/5 18:51
 */
class CLog {

    companion object {
        private val TAG: String = CLog::class.java.simpleName

        private val debug: Boolean = true

        fun i(tag: String = TAG, msg: String) {
            if (debug) {
                Log.i(tag, msg)
            }
        }

        fun d(tag: String = TAG, msg: String) {
            if (debug) {
                Log.d(tag, msg)
            }
        }

        fun e(tag: String = TAG, msg: String) {
            if (debug) {
                Log.e(tag, msg)
            }
        }
    }

}