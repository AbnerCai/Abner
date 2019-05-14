package top.abner.base.mvp

import android.os.Bundle
import top.abner.base.BaseActivity

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/18 14:14
 */
abstract class MvpActivity<P : BasePresenter<IBaseView>> : BaseActivity() {

    protected var mvpPresenter: P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mvpPresenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun createPresenter(): P



    override fun onDestroy() {
        super.onDestroy()
        if (mvpPresenter != null) {
            mvpPresenter?.detachView()
        }
    }

}