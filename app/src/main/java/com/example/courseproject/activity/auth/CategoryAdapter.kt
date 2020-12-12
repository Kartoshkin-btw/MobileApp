package com.example.courseproject.activity.auth

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.courseproject.activity.CategoriesFreeActivity
import com.example.courseproject.activity.CategoriesPaidActivity
import com.example.courseproject.activity.CategoriesPurchasedActivity

@Suppress("DEPRECATION")
internal class CategoryAdapter (
    var context: Activity,
    fm: FragmentManager,
    var totalTabs: Int,
    var name1: String,
    var name2: String
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChangeFreeActivity(context, name1, name2)
            }
            1 -> {
                ChangePurchasedActivity(context, name1, name2)
            }
            2 -> {
                ChangeUsersActivity(context, name1, name2)
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}