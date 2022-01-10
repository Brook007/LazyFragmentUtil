package com.brook.app.lazyfragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun viewpager(view: android.view.View) {
        startActivity(Intent(
            this,
            ViewpagerActivity::class.java
        ))
    }

    fun showHide(view: android.view.View) {
        startActivity(Intent(
            this,
            ShowHideFragmentTestActivity::class.java
        ))
    }
}