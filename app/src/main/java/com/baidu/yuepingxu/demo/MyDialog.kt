package com.baidu.yuepingxu.demo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_permission.*


/**
 * @author xuyueping
 * @date 2020-03-09
 * @describe
 */
class MyDialog(context: Context, id: Int = R.style.MyDialog) : Dialog(context, id) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_permission)
        cancel.setOnClickListener {
            dismiss()
        }
    }

}