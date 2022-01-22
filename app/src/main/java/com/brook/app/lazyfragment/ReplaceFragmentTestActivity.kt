package com.brook.app.lazyfragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ReplaceFragmentTestActivity : AppCompatActivity() {

    private var fragment: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showhide)
    }

    fun change(view: View) {
        if (fragment) {
            val fragmentB = BottomChildFragment.newInstance(1)
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_show_container, fragmentB)
            beginTransaction.commitNowAllowingStateLoss()
        } else {
            val fragmentA = BottomChildFragment.newInstance(0)
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_show_container, fragmentA)
            beginTransaction.commitNowAllowingStateLoss()
        }
        fragment = !fragment
    }

}