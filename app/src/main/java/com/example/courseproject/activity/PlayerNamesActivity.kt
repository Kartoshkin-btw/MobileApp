package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import com.example.courseproject.activity.auth.ChangeCategoryAuthActivity
import com.example.courseproject.activity.unauth.ChangeCategoryActivity
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
        playerName.setText(MainActivity.Name)
        button.setOnClickListener {
            val newIntent:Intent
            if (MainActivity.Role.isEmpty()) {
                newIntent = Intent(this, ChangeCategoryActivity::class.java)
            } else {
                newIntent = Intent(this, ChangeCategoryAuthActivity::class.java)
            }
            newIntent.putExtra("name1", playerName.text.toString())
            newIntent.putExtra("name2", playerName2.text.toString())
            startActivity(newIntent)
        }
    }
}