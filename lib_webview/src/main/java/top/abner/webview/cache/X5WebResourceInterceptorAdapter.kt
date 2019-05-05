package top.abner.webview.cache

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.InputStream

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/5 19:15
 */
class X5WebResourceInterceptorAdapter
    constructor(mWebResourceResponse: android.webkit.WebResourceResponse?) :
        com.tencent.smtt.export.external.interfaces.WebResourceResponse() {

    private var mWebResourceResponse: android.webkit.WebResourceResponse?

    init {
        this.mWebResourceResponse = mWebResourceResponse
    }


    override fun getMimeType(): String? {
        return mWebResourceResponse?.getMimeType()
    }

    override fun getData(): InputStream? {
        return mWebResourceResponse?.getData()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getStatusCode(): Int {
        return mWebResourceResponse?.getStatusCode() ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getResponseHeaders(): Map<String, String>? {
        return mWebResourceResponse?.getResponseHeaders()
    }

    override fun getEncoding(): String? {
        return mWebResourceResponse?.getEncoding()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getReasonPhrase(): String? {
        return mWebResourceResponse?.getReasonPhrase()
    }

}