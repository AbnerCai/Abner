package top.abner.webview

import android.app.Application
import android.util.Log
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback

open class X5App : Application() {

    val TAG = "X5APP"

    override fun onCreate() {
        super.onCreate()

         class InitCallback : PreInitCallback {
             override fun onCoreInitFinished() {
                 Log.i(TAG, "init")
             }

             override fun onViewInitFinished(p0: Boolean) {
                 Log.i(TAG, "init: " + p0)
             }
         }

        QbSdk.initX5Environment(this, InitCallback())
    }

}