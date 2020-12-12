package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesAuthActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private lateinit var balance: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_auth)
        mainText.text="Категории"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            startActivity(newIntent)
        }

        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getBalance(MainActivity.Token)
        response.enqueue(object: Callback<Int> {

            override fun onFailure(call: Call<Int>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(this@CategoriesAuthActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        balanceText.visibility = View.VISIBLE
                        balance = "Balance: " + res.toString()
                        balanceText.text = balance
                    }
                }
            }
        } )


        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)
        tabLayout.addTab(tabLayout.newTab().setText("Бесплатные"))
        tabLayout.addTab(tabLayout.newTab().setText("Платные"))
        tabLayout.addTab(tabLayout.newTab().setText("Купленные"))
        tabLayout.addTab(tabLayout.newTab().setText("Мои"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = CategoriesAuthAdapter(this, supportFragmentManager,
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