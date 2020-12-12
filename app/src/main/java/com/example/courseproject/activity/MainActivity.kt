package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import com.example.courseproject.activity.auth.CategoriesAuthActivity
import com.example.courseproject.activity.auth.ChangeCategoryAuthActivity
import com.example.courseproject.activity.unauth.CategoriesActivity
import com.example.courseproject.activity.unauth.ChangeCategoryActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_player_names.*
import kotlinx.android.synthetic.main.mainappbar.*

class MainActivity : AppCompatActivity() {
    companion object {
        var Token = ""
        var Role = ""
        var Name = ""
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
            val newIntent:Intent
            if (MainActivity.Role.isEmpty()) {
                newIntent = Intent(this, CategoriesActivity::class.java)
            } else {
                newIntent = Intent(this, CategoriesAuthActivity::class.java)
            }
            startActivity(newIntent)
        }
        button4.setOnClickListener {
            val newIntent = Intent (this, LoginActivity::class.java)
            startActivity(newIntent)
        }
    }
}