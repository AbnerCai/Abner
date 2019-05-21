package top.abner.base.mvp.presenter

import top.abner.base.mvp.view.IBaseView

/**
 * P 层，负责业务逻辑处理
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/16 18:21
 */
interface IBasePresenter<T : IBaseView>  {
    /**
     * 绑定view，一般在初始化中调用该方法
     * @param mView
     */
    fun attachView(mView: T)

    /**
     * 断开view，一般在onDestroy中调用
     */
    fun detachView()

    /**
     * 是否与 View 建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与 View 建立连接
     * @return
     */
    fun isViewAttached(): Boolean
}