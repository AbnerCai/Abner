package top.abner.webview.cache

import android.content.Context
import android.util.Log
import android.webkit.WebResourceResponse
import top.abner.webview.util.CLog
import java.io.FileNotFoundException
import java.io.InputStream

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/5 18:47
 */
class WebResourceInterceptor : ResourceInterceptor{

    companion object {
        private var TAG: String = WebResourceInterceptor::class.java.simpleName

        val instance = SingletonHolder.holder
    }

    /**
     * 单例：静态内部类
     * */
    private object SingletonHolder {
        val holder = WebResourceInterceptor()
    }

    override fun init(context: Context) {
        CacheHelper.getInstance().initDiskCache(context)
    }

    override fun urlIntercept(url: String?): WebResourceResponse? {
        CLog.i(TAG, "url: " + url)
        var response: WebResourceResponse? = null
        // TODO： 判断后缀
        if (null == url || "".equals(url)) {
            return null
        }
        if (url.endsWith(".js")) {
            response = getWebResourceResponse(url,"text/javascript", "UTF-8");
            if (null == response) {
                CLog.i(TAG, "js: 下载缓存: " + url)
                CacheHelper.getInstance().setFileToDisk(url, object: CacheHelper.CacheCallback{
                    override fun onFinish(fileUrl: String?) {
                        Log.i(TAG, "下载完成 fileUrl: " + fileUrl)
                    }

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                })
            } else {
                CLog.i(TAG, "js: 使用缓存: " + url)
            }
        }
        if (url.endsWith(".css")) {
            response = getWebResourceResponse(url,"text/css", "UTF-8");
            if (null == response) {
                CLog.i(TAG, "css: 下载缓存: " + url)
                CacheHelper.getInstance().setFileToDisk(url, object: CacheHelper.CacheCallback{
                    override fun onFinish(fileUrl: String?) {
                        Log.i(TAG, "下载完成 fileUrl: " + fileUrl)
                    }

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                })
            } else {
                CLog.i(TAG, "css: 使用缓存: " + url)
            }
        }
        if (url.endsWith(".png")) {
            response = getWebResourceResponse(url,"image/png", "UTF-8");
            if (null == response) {
                CLog.i(TAG, "png: 下载缓存: " + url)
                CacheHelper.getInstance().setFileToDisk(url, object: CacheHelper.CacheCallback{
                    override fun onFinish(fileUrl: String?) {
                        Log.i(TAG, "下载完成 fileUrl: " + fileUrl)
                    }

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                })
            } else {
                CLog.i(TAG, "png: 使用缓存: " + url)
            }
        }
        return response
    }

    /**
     * 获取资源文件
     * */
    private fun getWebResourceResponse(url: String, mime: String, style: String): WebResourceResponse? {
        var response: WebResourceResponse? = null
        var input: InputStream? = CacheHelper.getInstance().getInputStreamFromDisk(url)
        if (null == input) {
            return null
        }
        try {
            response = WebResourceResponse(mime, style, input)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return response
    }
}