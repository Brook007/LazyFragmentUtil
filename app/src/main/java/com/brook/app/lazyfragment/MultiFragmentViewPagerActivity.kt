package com.brook.app.lazyfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MultiFragmentViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_viewpager)

        val fragmentB = NestedViewPagerFragment.newInstance()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.container, fragmentB)
        beginTransaction.commitNowAllowingStateLoss()
    }
}