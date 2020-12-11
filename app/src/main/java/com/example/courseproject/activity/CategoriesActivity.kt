package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.courseproject.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.appbar.*
class CategoriesActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        mainText.text="Категории"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            startActivity(newIntent)
        }
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
        tabLayout.addTab(tabLayout.newTab().setText("Бесплатные"))
        tabLayout.addTab(tabLayout.newTab().setText("Платные"))
        tabLayout.addTab(tabLayout.newTab().setText("Купленные"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = Adapter(this, supportFragmentManager,
            tabLayout.tabCount)
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
