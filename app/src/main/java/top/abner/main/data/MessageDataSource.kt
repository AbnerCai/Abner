package top.abner.main.data

import android.util.Log
import androidx.paging.PositionalDataSource
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import top.abner.main.data.entity.Message
import top.abner.main.data.network.HttpUtils

class MessageDataSource : PositionalDataSource<Message>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Message>) {
        callback.onResult(loadData(0, 10),0,10);
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Message>) {
        callback.onResult(loadData(params.startPosition, 10));
    }

    private fun loadData(start: Int, count: Int): List<Message> {
        val result = HttpUtils.get("http://api.nebulous.cn/v1/digest").body()?.string().toString()

        Log.i("", result)

        val body = JSON.parseObject(result)

        val code = body.getIntValue("code")
        if (code == 200) {
            val data = body.getJSONArray("data")

            val originalData = JSONObject.parseArray(JSON.toJSONString(data)).toJavaList(Message::class.java)
            if (start + count >= originalData.size) {
                return originalData.subList(start, originalData.size)
            } else {
                return originalData.subList(start, start + count)
            }
        } else {

        }
        return ArrayList<Message>()
    }
}