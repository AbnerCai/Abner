package top.abner.webview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_x5webview.*
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import top.abner.webview.cache.WebResourceInterceptor
import top.abner.webview.cache.X5WebResourceInterceptorAdapter
import java.lang.NullPointerException


/**
 * X5 实现的 WevView
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/19 10:08
 */
class X5WebViewActivity : AppCompatActivity() {


    companion object {
        fun launch(context: Context, url: String) {
            val intent = Intent(context, X5WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5webview)
        initView()

        WebResourceInterceptor.instance.init(this)

        initData()
    }

    private fun initData() {
        val url = intent.getStringExtra("url");
        if (url.isNotEmpty()) {
            webview.loadUrl(url);
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

        // 其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK) //关闭webview中缓存
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

            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                var response: android.webkit.WebResourceResponse? =
                    WebResourceInterceptor.instance.urlIntercept(url);
                if (null != response) {
                    return X5WebResourceInterceptorAdapter(response)
                }
                return super.shouldInterceptRequest(view, url)
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
}
