package com.example.courseproject.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_all.*
import kotlinx.android.synthetic.main.activity_categories_all.recyclerView
import kotlinx.android.synthetic.main.activity_categories_free.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesAllActivity : Fragment() {
    private var list = mutableListOf<CategoryResponse>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_categories_all, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getFreeCategories()
        response.enqueue(object: Callback<List<CategoryResponse>> {

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(this@CategoriesAllActivity.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        list = res.toMutableList()
                    }
                }
            }

        } )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter(list)
        }
    }
}