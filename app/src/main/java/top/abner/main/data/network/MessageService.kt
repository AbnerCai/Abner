package top.abner.main.data.network

import retrofit2.Call
import retrofit2.http.GET
import top.abner.main.data.Resp
import top.abner.main.data.entity.Message


interface MessageService {

    @GET("v1/digest")
    fun listMessage(): Call<Resp<List<Message>>>
}