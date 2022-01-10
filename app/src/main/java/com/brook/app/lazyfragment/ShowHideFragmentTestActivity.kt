package com.brook.app.lazyfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ShowHideFragmentTestActivity : AppCompatActivity() {

    private var fragmentA:Fragment? = null
    private var fragmentB:Fragment? = null
    private var fragment:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showhide)
    }

    fun change(view: android.view.View) {
        if (fragment) {
            if (fragmentB == null) {
                fragmentB = Parent2Fragment.newInstance(1)
            }
            val beginTransaction = supportFragmentManager.beginTransaction()
            if (!fragmentB!!.isAdded) {
                beginTransaction.add(R.id.fragment_show_container, fragmentB!!)
            }
            if (fragmentA != null) {
                beginTransaction.hide(fragmentA!!)
            }
            beginTransaction.show(fragmentB!!)
            beginTransaction.commitNowAllowingStateLoss()
        } else {
            if (fragmentA == null) {
                fragmentA = ParentFragment.newInstance(0)
            }
            val beginTransaction = supportFragmentManager.beginTransaction()
            if (!fragmentA!!.isAdded) {
                beginTransaction.add(R.id.fragment_show_container, fragmentA!!)
            }
            if (fragmentB != null) {
                beginTransaction.hide(fragmentB!!)
            }
            beginTransaction.show(fragmentA!!)
            beginTransaction.commitNowAllowingStateLoss()
        }
        fragment = !fragment
    }

}