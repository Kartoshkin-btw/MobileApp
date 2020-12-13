package com.example.courseproject.activity.auth

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.courseproject.activity.CategoriesFreeActivity

@Suppress("DEPRECATION")
internal class CategoriesAuthAdapter (
    val context: Activity,
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
                CategoriesPaidAuthActivity(context)
            }
            2 -> {
                CategoriesPurchasedActivity()
            }
            3 -> {
                CategoriesUsersActivity()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}