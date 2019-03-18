package top.abner.main.data.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/14 14:16
 */
class ServiceManager {

    companion object {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.nebulous.cn/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
}