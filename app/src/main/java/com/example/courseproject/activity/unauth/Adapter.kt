package com.example.courseproject.activity.unauth

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.courseproject.activity.CategoriesFreeActivity

@Suppress("DEPRECATION")
internal class Adapter (
    var context: Activity,
    fm: FragmentManager,
    var totalTabs: Int
    ) :
    FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    CategoriesFreeActivity()
                }
                1 -> {
                    CategoriesPaidActivity()
                }
                else -> getItem(position)
            }
        }
        override fun getCount(): Int {
            return totalTabs
        }
    }