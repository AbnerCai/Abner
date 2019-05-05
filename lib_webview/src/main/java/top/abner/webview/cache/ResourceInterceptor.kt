package top.abner.webview.cache

import android.content.Context
import android.webkit.WebResourceResponse

/**
 * 资源拦截器.
 *
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/5 18:44
 */
interface ResourceInterceptor {

    /**
     * 初始化
     * */
    fun init(context: Context)

    /**
     * url 拦截
     * */
    fun urlIntercept(url: String?) : WebResourceResponse?
}