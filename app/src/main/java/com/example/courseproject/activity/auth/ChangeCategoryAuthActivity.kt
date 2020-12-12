package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.courseproject.R
import com.example.courseproject.activity.Adapter
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.PlayerNamesActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.appbar.*

class ChangeCategoryAuthActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_category_auth)
        mainText.text="Выбор категории"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, PlayerNamesActivity::class.java)
            startActivity(newIntent)
        }
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
        tabLayout.addTab(tabLayout.newTab().setText("Бесплатные"))
        tabLayout.addTab(tabLayout.newTab().setText("Купленные"))
        tabLayout.addTab(tabLayout.newTab().setText("Мои"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = CategoryAdapter(this, supportFragmentManager,
            tabLayout.tabCount, intent.getStringExtra("name1").toString(), intent.getStringExtra("name2").toString())
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}