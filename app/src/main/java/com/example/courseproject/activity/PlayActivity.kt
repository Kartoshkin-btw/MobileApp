package com.example.courseproject.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.auth.ChangeCategoryAuthActivity
import com.example.courseproject.activity.unauth.ChangeCategoryActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.QuestionResponse
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.playerName
import kotlinx.android.synthetic.main.activity_play.playerName2
import kotlinx.android.synthetic.main.activity_player_names.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayActivity : AppCompatActivity() {

    private var list = mutableListOf<QuestionResponse>()
    private var number = 0
    private var turn = "1"
    private var loseStrike = false
    private fun setQuestion(){
        number++
        if (number < list.size){
            question.text = list[number].text
        } else {
            number = 0
            question.text = list[number].text
        }
    }

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
            val newIntent:Intent
            if (MainActivity.Role.isEmpty()) {
                newIntent = Intent(this, ChangeCategoryActivity::class.java)
            } else {
                newIntent = Intent(this, ChangeCategoryAuthActivity::class.java)
            }
            startActivity(newIntent)
        }

        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getQuestions(intent.getStringExtra("categoryID").toString().toInt())
        response.enqueue(object: Callback<List<QuestionResponse>> {

            override fun onFailure(call: Call<List<QuestionResponse>>, t: Throwable) {
                Log.i("ITEM", "Failure")
            }

            override fun onResponse(
                call: Call<List<QuestionResponse>>,
                response: Response<List<QuestionResponse>>
            ) {
                val res = response.body()

                if (response.code() == 200) {
                    if (res != null) {
                        list = res.toMutableList()
                    }
                }
            }
        })

        playerName.text = intent.getStringExtra("name1")
        playerName2.text = intent.getStringExtra("name2")
        score1.text = "0"
        score2.text = "0"
        startButton.setOnClickListener {
            startButton.visibility = View.INVISIBLE
            question.visibility = View.VISIBLE
            question.text = list[number].text
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
            if (loseStrike){
                setQuestion()
                loseStrike = false
            } else
                loseStrike = true
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
                setQuestion()
                turn = "2"
            } else {
                linearLayout2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                linearLayout.setBackgroundColor(Color.parseColor("#FA5858"))
                score2.text = (score2.text.toString().toInt() + 1).toString()
                setQuestion()
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