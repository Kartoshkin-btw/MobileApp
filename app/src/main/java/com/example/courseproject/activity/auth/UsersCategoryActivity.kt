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
import com.example.courseproject.activity.PlayActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.activity.RecyclerAdapterNoClick
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_free.*
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