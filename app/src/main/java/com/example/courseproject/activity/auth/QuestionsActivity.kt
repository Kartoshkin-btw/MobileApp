package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.CategoryBody
import com.example.courseproject.body.EditCategoryBody
import com.example.courseproject.body.QuestionBody
import com.example.courseproject.response.CategoryResponse
import com.example.courseproject.response.QuestionResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_questions.recyclerView
import kotlinx.android.synthetic.main.activity_users_category.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Удаление")
            builder.setMessage("Эта категория будет удалена. Вы уверены?")
            builder.setPositiveButton("Удалить") { dialogInterface, i ->
                recyclerView.layoutManager = LinearLayoutManager(application)
                val request = Client.buildService(JsonPlaceHolderApi::class.java)
                val response = request.deleteCategory(
                    MainActivity.Token,
                    intent.getStringExtra("categoryID").toString().toInt()
                )
                response.enqueue(object : Callback<Void> {

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@QuestionsActivity, "${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.code() == 200) {
                        }
                    }
                })
                val newIntent = Intent (this, UsersCategoryActivity::class.java)
                startActivity(newIntent)
            }
            builder.setNegativeButton("Отмена",null)
            builder.show()
        }
        editButton.setOnClickListener {
            if (MainActivity.Role == "User") {
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                builder.setTitle("Редактирование категории")
                val dialogLayout = inflater.inflate(R.layout.alert_dialog_question, null)
                val editText = dialogLayout.findViewById<EditText>(R.id.editText)
                editText.hint = "Название категории"
                editText.setText(intent.getStringExtra("categoryName"))
                builder.setView(dialogLayout)
                builder.setPositiveButton("Изменить") { dialogInterface, i ->
                    recyclerView.layoutManager = LinearLayoutManager(application)
                    val request = Client.buildService(JsonPlaceHolderApi::class.java)
                    val editCategoryBody = EditCategoryBody(intent.getStringExtra("categoryID").toString().toInt(),editText.text.toString(), 0, false)
                    val response = request.editCategory(MainActivity.Token, editCategoryBody)
                    response.enqueue(object : Callback<Void> {

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(
                                this@QuestionsActivity,
                                "${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            if (response.code() == 200) {
                            }
                        }
                    })
                }
                builder.setNegativeButton("Отмена",null)
                builder.show()
            }else if (MainActivity.Role == "Admin"){
                val newIntent = Intent(this, CreateAdminCategoryActivity::class.java).apply {
                    putExtra("categoryID", intent.getStringExtra("categoryID"))
                    putExtra("categoryName", intent.getStringExtra("categoryName"))
                }
                startActivity(newIntent)
            }
        }
        createQuestion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Введите вопрос")
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_question, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            editText.hint = "Текст вопроса"
            builder.setView(dialogLayout)
            builder.setPositiveButton("Создать"){dialogInterface, i ->
                recyclerView.layoutManager = LinearLayoutManager(application)
                val request = Client.buildService(JsonPlaceHolderApi::class.java)
                val questionBody = QuestionBody(editText.text.toString(), intent.getStringExtra("categoryID").toString().toInt())
                val response = request.createQuestion(MainActivity.Token, questionBody)
                response.enqueue(object : Callback<Void> {

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@QuestionsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.code() == 200) {
                        }
                    }
                })
            }
            builder.setNegativeButton("Отмена",null)
            builder.show()
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