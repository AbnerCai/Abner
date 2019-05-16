package top.abner.base.mvp.view

/**
 * V 层，负责 UI 显示相关
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/18 11:52
 */
interface IBaseView {
    
    /**
     * 显示加载中.
     */
    open fun showLoading()

    /**
     * 隐藏加载中.
     */
    open fun dismissLoading()

    /**
     * 显示 Toast
     * @param message 显示消息
     */
    open fun showToast(message: String)

    /**
     * 失败异常处理
     * @param e 异常信息
     */
    open fun onError(e: Exception)
}