package com.baidu.yuepingxu.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


/**
 * @author xuyueping
 * @date 2020-03-10
 * @describe
 */
class TAxctivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ta)
    }

    fun back(view: View) {
        finish()
    }
}