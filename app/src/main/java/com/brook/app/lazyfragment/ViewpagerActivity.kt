package com.brook.app.lazyfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class ViewpagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_viewpager)

        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return 6
            }

            override fun getItem(position: Int): Fragment {
                return ParentFragment.newInstance(position)
            }

        }
    }
}