package com.example.courseproject.activity

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.courseproject.response.CategoryResponse

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
                    CategoriesAllActivity()
                }
                1 -> {
                    CategoriesFreeActivity()
                }
                2 -> {
                    CategoriesPaidActivity()
                }
                3 -> {
                    CategoriesPurchasedActivity()
                }
                else -> getItem(position)
            }
        }
        override fun getCount(): Int {
            return totalTabs
        }
}