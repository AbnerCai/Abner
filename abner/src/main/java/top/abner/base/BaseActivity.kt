package top.abner.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/18 15:17
 */
abstract class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 加载中
     * */
    abstract fun showLoading();

    /**
     *
     * */
    abstract fun showLoadSuccess();

    /**
     *
     * */
    abstract fun showLoadFailed();

    /**
     *
     * */
    abstract fun showEmpty();
}