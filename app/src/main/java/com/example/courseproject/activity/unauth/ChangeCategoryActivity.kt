package com.example.courseproject.activity.unauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.PlayActivity
import com.example.courseproject.activity.PlayerNamesActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_change_category.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeCategoryActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

    private var list = mutableListOf<CategoryResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_category)
        mainText.text="Ответь за 5 секунд"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, PlayerNamesActivity::class.java)
            startActivity(newIntent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getFreeCategories()
        response.enqueue(object : Callback<List<CategoryResponse>> {

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                Log.i("ITEM", "Failure")
            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                val res = response.body()

                if (response.code() == 200) {
                    if (res != null) {
                        list = res.toMutableList()
                        recyclerView.adapter =
                            RecyclerAdapter(false, list, this@ChangeCategoryActivity)
                    }
                }
            }
        })
    }

    override fun onItemClick(position: Int) {
        val newIntent = Intent (this, PlayActivity::class.java).apply {
                putExtra("name1", intent.getStringExtra("name1"))
                putExtra("name2", intent.getStringExtra("name2"))
                putExtra("categoryID", list[position].id.toString())
            }
        startActivity(newIntent)
    }
}