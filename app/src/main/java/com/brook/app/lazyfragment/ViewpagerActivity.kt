package com.brook.app.lazyfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_viewpager)

        val fragmentB = ViewPagerFragment.newInstance(0)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.container, fragmentB)
        beginTransaction.commitNowAllowingStateLoss()
    }
}