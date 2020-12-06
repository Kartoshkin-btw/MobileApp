package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_player_names.*
import kotlinx.android.synthetic.main.appbar.*

class ChangeCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_category)
        mainText.text="Ответь за 5 секунд"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, PlayerNamesActivity::class.java)
            startActivity(newIntent)
        }
        button.setOnClickListener {
            val newIntent = Intent (this, PlayActivity::class.java).apply {
                putExtra("name1", intent.getStringExtra("name1"))
                putExtra("name2", intent.getStringExtra("name2"))
            }
            startActivity(newIntent)
        }
    }
}