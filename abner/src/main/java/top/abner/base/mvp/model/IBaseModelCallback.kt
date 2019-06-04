package top.abner.base.mvp.model

/**
 * M 层处理结果回调，负责与 P 层交互
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/14 15:23
 */
interface IBaseModelCallback<T> {

    /**
     * 成功回调.
     * @param data
     */
    fun onSuccess(data: T)

    /**
     * 失败回调.
     * @param failure
     */
    fun onFailure(code: Int, failure: String)

    /**
     * 错误回调.
     * @param error
     */
    fun onError(error: String)

    /**
     * 完成回调.
     */
    fun onComplete()
}
