package com.brook.app.lazyfragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun replace(view: View) {
        startActivity(
            Intent(
                this,
                ReplaceFragmentTestActivity::class.java
            )
        )
    }

    fun multiFragmentReplace(view: View) {
        startActivity(
            Intent(
                this,
                MultiFragmentReplaceActivity::class.java
            )
        )
    }

    fun showHide(view: View) {
        startActivity(
            Intent(
                this,
                ShowHideFragmentTestActivity::class.java
            )
        )
    }

    fun multiFragmentShowHide(view: View) {
        startActivity(
            Intent(
                this,
                MultiFragmentShowHideActivity::class.java
            )
        )
    }

    fun viewpager(view: View) {
        startActivity(
            Intent(
                this,
                ViewPagerActivity::class.java
            )
        )
    }

    fun multiViewPager(view: View) {
        startActivity(
            Intent(
                this,
                MultiFragmentViewPagerActivity::class.java
            )
        )
    }

    fun tabLayoutMultiViewPager(view: android.view.View) {
        startActivity(
            Intent(
                this,
                ViewPagerWithTabLayoutActivity::class.java
            )
        )
    }
}