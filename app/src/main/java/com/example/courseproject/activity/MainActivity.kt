package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mainappbar.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val Token = "Bearer "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainText.text="Главная"
        button.setOnClickListener {
            val newIntent = Intent (this, PlayerNamesActivity::class.java)
            startActivity(newIntent)
        }
        button2.setOnClickListener {
            val newIntent = Intent (this, RulesActivity::class.java)
            startActivity(newIntent)
        }
        button3.setOnClickListener {
            val newIntent = Intent (this, CategoriesActivity::class.java)
            startActivity(newIntent)
        }
        button4.setOnClickListener {
            val newIntent = Intent (this, LoginActivity::class.java)
            startActivity(newIntent)
        }
    }
}