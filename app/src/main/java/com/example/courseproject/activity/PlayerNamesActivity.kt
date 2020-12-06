package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_player_names.*
import kotlinx.android.synthetic.main.appbar.*

class PlayerNamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_names)
        mainText.text="Ответь за 5 секунд"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            startActivity(newIntent)
        }
        button.setOnClickListener {
            val newIntent = Intent (this, ChangeCategoryActivity::class.java).apply {
                putExtra("name1", playerName.text.toString())
                putExtra("name2", playerName2.text.toString())
            }
            startActivity(newIntent)
        }
    }
}