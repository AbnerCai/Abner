package top.abner.main.data.network

import okhttp3.*
import java.util.concurrent.TimeUnit


/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/14 15:18
 */
class HttpUtils {

    companion object {

        private val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

        fun get(url: String, callback: Callback) {
            val request = Request.Builder()
                    .url(url)
                    .build()
            client.newCall(request).enqueue(callback)
        }

        fun get(url: String): Response {
            val request = Request.Builder()
                    .url(url)
                    .build()
            return client.newCall(request).execute()
        }

        private val JSON = MediaType.parse("application/json; charset=utf-8")

        fun post(url: String, json: String, callback: Callback) {
            val body = RequestBody.create(JSON, json)
            val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()
            client.newCall(request).enqueue(callback)
        }
    }
}