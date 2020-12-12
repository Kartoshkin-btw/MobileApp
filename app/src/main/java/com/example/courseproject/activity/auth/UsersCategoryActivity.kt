package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import kotlinx.android.synthetic.main.appbar.*

class UsersCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_category)
        mainText.text="Мои категории"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, Profile::class.java)
            startActivity(newIntent)
        }
    }
}