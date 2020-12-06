package com.example.courseproject.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.courseproject.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.appbar.*

class PlayActivity : AppCompatActivity() {
    var countdown_timer = object :CountDownTimer(5000, 10){
        override fun onTick(millisUntilFinished: Long) {
            timer.text = (millisUntilFinished/1000).toString() + ":" + (millisUntilFinished/10-(millisUntilFinished/1000*100)).toString()
        }
        override fun onFinish() {
            timer.text = "Время вышло!"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        mainText.text="Ответь за 5 секунд"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, ChangeCategoryActivity::class.java)
            startActivity(newIntent)
        }

        playerName.text = intent.getStringExtra("name1")
        playerName2.text = intent.getStringExtra("name2")
        score1.text = "0"
        score2.text = "0"

        var turn = "1"
        startButton.setOnClickListener {
            startButton.visibility = View.INVISIBLE
            question.visibility = View.VISIBLE
            readyButton.visibility = View.VISIBLE
            linearLayout.setBackgroundColor(Color.parseColor("#FA5858"))
        }
        readyButton.setOnClickListener {
            readyButton.visibility = View.INVISIBLE
            noAns.visibility = View.VISIBLE
            ans.visibility = View.VISIBLE
            countdown_timer.start()
        }
        noAns.setOnClickListener {
            readyButton.visibility = View.VISIBLE
            noAns.visibility = View.INVISIBLE
            ans.visibility = View.INVISIBLE
            timer.text = ""
            if (turn == "1"){
                linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout2.setBackgroundColor(Color.parseColor("#FA5858"))
                turn = "2"
            } else {
                linearLayout2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout.setBackgroundColor(Color.parseColor("#FA5858"))
                turn = "1"
            }
        }
        ans.setOnClickListener {
            readyButton.visibility = View.VISIBLE
            noAns.visibility = View.INVISIBLE
            ans.visibility = View.INVISIBLE
            timer.text = ""
            if (turn == "1"){
                linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout2.setBackgroundColor(Color.parseColor("#FA5858"))
                score1.text = (score1.text.toString().toInt() + 1).toString()
                turn = "2"
            } else {
                linearLayout2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout.setBackgroundColor(Color.parseColor("#FA5858"))
                score2.text = (score2.text.toString().toInt() + 1).toString()
                turn = "1"
            }
            if (score1.text == "20") {
                score1.text = "Win"
            }
            if (score2.text == "20") {
                score2.text = "Win"
            }
        }
    }
}