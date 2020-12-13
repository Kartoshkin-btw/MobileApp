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
import com.example.courseproject.activity.PlayActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.activity.RecyclerAdapterNoClick
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.CategoryBody
import com.example.courseproject.body.QuestionBody
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_free.*
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_users_category.*
import kotlinx.android.synthetic.main.activity_users_category.recyclerView
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersCategoryActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

    private var list = mutableListOf<CategoryResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_category)
        mainText.text="Мои категории"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, Profile::class.java)
            startActivity(newIntent)
        }
        createButton.setOnClickListener {
            if (MainActivity.Role == "User") {
                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                builder.setTitle("Введите название категории")
                val dialogLayout = inflater.inflate(R.layout.alert_dialog_question, null)
                val editText = dialogLayout.findViewById<EditText>(R.id.editText)
                editText.hint = "Название категории"
                builder.setView(dialogLayout)
                builder.setPositiveButton("Создать") { dialogInterface, i ->
                    recyclerView.layoutManager = LinearLayoutManager(application)
                    val request = Client.buildService(JsonPlaceHolderApi::class.java)
                    val categoryBody = CategoryBody(editText.text.toString(), 0, false)
                    val response = request.createUserCategory(MainActivity.Token, categoryBody)
                    response.enqueue(object : Callback<Void> {

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(
                                this@UsersCategoryActivity,
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
                val newIntent = Intent(this, CreateAdminCategoryActivity::class.java)
                startActivity(newIntent)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(application)
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getCustomCategories(MainActivity.Token)
        response.enqueue(object: Callback<List<CategoryResponse>> {

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        list = res.toMutableList()
                        if (MainActivity.Role == "User")
                            recyclerView.adapter = RecyclerAdapter(false , list, this@UsersCategoryActivity)
                        else if (MainActivity.Role == "Admin"){
                            recyclerView.adapter = RecyclerAdapter(true , list, this@UsersCategoryActivity)
                        }
                    }
                }
            }
        } )
    }

    override fun onItemClick(position: Int) {
        val newIntent = Intent (this, QuestionsActivity::class.java).apply {
            putExtra("categoryName", list[position].title)
            putExtra("categoryID", list[position].id.toString())
        }
        startActivity(newIntent)
    }
}
