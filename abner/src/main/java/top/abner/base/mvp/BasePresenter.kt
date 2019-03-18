package top.abner.base.mvp

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/18 11:52
 */
class BasePresenter<V: BaseView> {

    var mvpView: V? = null

    fun attachView(mvpView: V) {
        this.mvpView = mvpView
    }


    fun detachView() {
        this.mvpView = null
    }
}