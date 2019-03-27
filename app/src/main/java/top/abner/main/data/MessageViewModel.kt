package top.abner.main.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import top.abner.main.data.entity.Message
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList


/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:32
 */
class MessageViewModel : ViewModel() {

    companion object {
        private val TAG = "MessageViewModel"

        private const val PAGE_SIZE = 15

        private const val ENABLE_PLACEHOLDERS = false
    }

    var config: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(10)                         //配置分页加载的数量
            .setEnablePlaceholders(false)     //配置是否启动PlaceHolders
            .setInitialLoadSizeHint(10)              //初始化加载的数量
            .build()

    var messages: LiveData<PagedList<Message>> = LivePagedListBuilder(MessageDataSourceFactory(), config)
            .build()


//    fun getMessages(): LiveData<List<Message>> {
//        if (messages == null) {
//            messages = MutableLiveData<List<Message>>()
//            loadMessages()
//        }
//        return messages as MutableLiveData<List<Message>>
//    }

//    private fun loadMessages() {
//        HttpUtils.get("http://api.nebulous.cn/v1/digest", object: Callback{
//            override fun onResponse(call: Call, response: Response) {
//                val result = response.body()?.string().toString()
//
//                Log.i(TAG, result)
//                val body = JSON.parseObject(result)
//
//                val code = body.getIntValue("code")
//                if (code == 200) {
//                    val data = body.getJSONArray("data")
//
//                    messages?.postValue(JSONObject.parseArray(JSON.toJSONString(data), Message::class.java))
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//        })
//    }

}
