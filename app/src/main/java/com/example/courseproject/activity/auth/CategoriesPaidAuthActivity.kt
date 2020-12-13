package com.example.courseproject.activity.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.PlayActivity
import com.example.courseproject.activity.RecyclerAdapter
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.BuyCategoryBody
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_free.*
import kotlinx.android.synthetic.main.activity_categories_free.recyclerView
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesPaidAuthActivity(val context: Activity) : Fragment(), RecyclerAdapter.OnItemClickListener {

    private var list = mutableListOf<CategoryResponse>()
    private var balance: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_categories_paid_auth, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList()
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getBalance(MainActivity.Token)
        response.enqueue(object: Callback<Int> {

            override fun onFailure(call: Call<Int>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        balance = res
                    }
                }
            }
        } )
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        val request = Client.buildService(JsonPlaceHolderApi::class.java)
//        val response = request.getNoPurchasedCategories(MainActivity.Token)
//        response.enqueue(object: Callback<List<CategoryResponse>> {
//
//            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable){
//                Log.i("ITEM", "Failure")
//                Toast.makeText(this@CategoriesPaidAuthActivity.context, "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(
//                call: Call<List<CategoryResponse>>,
//                response: Response<List<CategoryResponse>>
//            ) {
//                val res = response.body()
//
//                if(response.code() == 200){
//                    if (res != null) {
//                        list = res.toMutableList()
//                        recyclerView.adapter = RecyclerAdapter(true, list, this@CategoriesPaidAuthActivity)
//                    }
//                }
//            }
//
//        } )
    }

    override fun onItemClick(position: Int) {
        if (list[position].price.toString().toInt() <= balance) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Покупка")
            builder.setMessage("Вы уверены, что хотите купить эту категорию?")
            builder.setPositiveButton("Купить"){ dialogInterface, i ->
                val request = Client.buildService(JsonPlaceHolderApi::class.java)
                val buyCategoryBody = BuyCategoryBody(list[position].id.toString())
                val response = request.buyCategory(MainActivity.Token, buyCategoryBody)
                response.enqueue(object : Callback<Void> {

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@CategoriesPaidAuthActivity.context,
                            "${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.code() == 200) {
                            setList()
                            val newIntent = Intent(context, CategoriesAuthActivity::class.java)
                            startActivity(newIntent)
                        }
                    }
                })
            }
            builder.setNegativeButton("Отмена",null)
            builder.show()
        } else {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Покупка")
            builder.setMessage("У вас не достаточный баланс")
            builder.setPositiveButton("Ок", null)
            builder.show()
        }
    }
    private fun setList(){
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getNoPurchasedCategories(MainActivity.Token)
        response.enqueue(object: Callback<List<CategoryResponse>> {

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(this@CategoriesPaidAuthActivity.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        list = res.toMutableList()
                        recyclerView.adapter = RecyclerAdapter(true, list, this@CategoriesPaidAuthActivity)
                    }
                }
            }

        } )
    }
}