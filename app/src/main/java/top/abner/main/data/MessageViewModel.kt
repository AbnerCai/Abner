package top.abner.main.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import top.abner.main.data.entity.Message
import top.abner.main.data.network.HttpUtils
import java.io.IOException

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:32
 */
class MessageViewModel : ViewModel() {

    val TAG = "MessageViewModel"

    var messages: MutableLiveData<List<Message>>? = null

    fun getMessages(): LiveData<List<Message>> {
        if (messages == null) {
            messages = MutableLiveData<List<Message>>()
            loadMessages()
        }
        return messages as MutableLiveData<List<Message>>
    }

    private fun loadMessages() {
        HttpUtils.get("http://api.nebulous.cn/v1/digest", object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string().toString()
                Log.i(TAG, result)
                val body = JSON.parseObject(result)
                val code = body.getIntValue("code")
                if (code == 200) {
                    val data = body.getJSONArray("data")

                    messages?.postValue(JSONObject.parseArray(JSON.toJSONString(data), Message::class.java))
                } else {

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

}
