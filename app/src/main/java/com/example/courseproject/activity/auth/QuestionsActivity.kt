package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.CategoryResponse
import com.example.courseproject.response.QuestionResponse
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_questions.recyclerView
import kotlinx.android.synthetic.main.activity_users_category.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsActivity : AppCompatActivity(), QuestionsAdapter.OnItemClickListener {

    private var list = mutableListOf<QuestionResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        mainText.text=intent.getStringExtra("categoryName")
        imageButton.setOnClickListener {
            val newIntent = Intent (this, UsersCategoryActivity::class.java)
            startActivity(newIntent)
        }
        deleteButton.setOnClickListener {

        }
        editButton.setOnClickListener {

        }
        createQuestion.setOnClickListener {

        }
        recyclerView.layoutManager = LinearLayoutManager(application)
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getQuestions(intent.getStringExtra("categoryID").toString().toInt())
        response.enqueue(object: Callback<List<QuestionResponse>> {

            override fun onFailure(call: Call<List<QuestionResponse>>, t: Throwable) {
                Log.i("ITEM", "Failure")
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<QuestionResponse>>,
                response: Response<List<QuestionResponse>>
            ) {
                val res = response.body()

                if (response.code() == 200) {
                    if (res != null) {
                        list = res.toMutableList()
                        recyclerView.adapter = QuestionsAdapter(list, this@QuestionsActivity)
                    }
                }
            }
        })
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}