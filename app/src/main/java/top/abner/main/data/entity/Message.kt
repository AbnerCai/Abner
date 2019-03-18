package top.abner.main.data.entity

/**
 * 留言信息
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:30
 */
data class Message(
        var id: Int?,
        var title: String?,
        var content: String,
        var provenance: String?,
        var readQuantity: Int?,
        var createTime: String?
        ) {
}