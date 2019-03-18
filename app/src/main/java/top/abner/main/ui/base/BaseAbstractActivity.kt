package top.abner.main.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:32
 */
abstract class BaseAbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun initView()

    abstract fun initData()
}
