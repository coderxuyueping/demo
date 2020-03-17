package com.baidu.yuepingxu.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dialog: MyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialog = MyDialog(this)
        click.setOnClickListener {
            if(dialog?.isShowing != true){
                dialog?.show()
            }
            click.postDelayed(object :Runnable{
                override fun run() {
                    startActivity(Intent(this@MainActivity, TAxctivity::class.java))
                }

            }, 2000)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("xuyueping", "onPause")
        dialog?.dismiss()
    }
}
