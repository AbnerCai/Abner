package top.abner.webview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import kotlinx.android.synthetic.main.activity_webview.*
import top.abner.webview.cache.CacheHelper
import top.abner.webview.cache.WebResourceInterceptor
import top.abner.webview.util.NetUtils
import java.io.FileNotFoundException
import java.io.InputStream

/**
 * WevView
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/19 10:08
 */
class WebViewActivity : AppCompatActivity() {


    companion object {
        fun launch(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }

        private var TAG: String = WebViewActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_webview)

        WebResourceInterceptor.instance.init(this)

        initView()

        initData()
    }

    private fun initData() {
        val url = intent.getStringExtra("url");
        // TODO: 校验 url 规则
        if (url.isNotEmpty()) {
            webview.loadUrl(url);
        } else {
            // TODO: 加载本地错误页面
        }
    }

    private fun initView() {
        // 声明WebSettings子类
        val webSettings = webview.getSettings()

        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true)

        // 设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true) //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true) // 缩放至屏幕的大小

        // 缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false) //隐藏原生的缩放控件

        // 浏览器缓存
        // 缓存模式如下：
        // LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        // LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        // LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        // LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        if (NetUtils.isConnected(applicationContext)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT)
        } else {
            // 无网络，则从本地获取，即离线加载
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
        }

        // 其他细节操作
        webSettings.setAllowFileAccess(true) //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true) //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true) //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8")//设置编码格式

        webview.webChromeClient = object : WebChromeClient() {

        }

        webview.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                try {
                    if (url!!.startsWith("http://")
                        || url!!.startsWith("https://")) {
                        webview.loadUrl(url)
                        return true
                    }
                } catch (e: Exception) {
                    return false
                }

                try {
                    if (url!!.startsWith("baiduboxapp://")
                        || url!!.startsWith("baiduboxlite://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                var response: WebResourceResponse? = WebResourceInterceptor.instance.urlIntercept(url);
                return response ?: super.shouldInterceptRequest(view, url)
            }

        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack())
            webview.goBack()
        else
            super.onBackPressed()
    }

    override fun onDestroy() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webview.clearHistory()
            (webview.getParent() as ViewGroup).removeView(webview)
            webview.destroy()
        }
        super.onDestroy()
    }



    fun clearWebViewCache() {
        // 清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}
