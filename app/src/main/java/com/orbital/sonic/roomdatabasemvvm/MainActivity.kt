package com.orbital.sonic.roomdatabasemvvm

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.orbital.sonic.roomdatabasemvvm.activity.AlarmActivity
import com.orbital.sonic.roomdatabasemvvm.activity.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnAlarm:TextView =findViewById(R.id.btnAlarm)
        btnAlarm.setOnClickListener{
            startActivity(Intent(this, AlarmActivity::class.java))
        }
    }
}