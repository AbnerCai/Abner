package top.abner.main.data

import androidx.paging.DataSource
import top.abner.main.data.entity.Message

class MessageDataSourceFactory : DataSource.Factory<Integer, Message>() {

    override fun create(): DataSource<Integer, Message> {
        return MessageDataSource() as DataSource<Integer, Message>
    }

}