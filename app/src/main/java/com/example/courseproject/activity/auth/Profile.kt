package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.LkResponse
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mainText.text="Личный кабинет"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            startActivity(newIntent)
        }
        if (MainActivity.Role == "Admin")
            statisticButton.visibility = View.VISIBLE
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getLk(MainActivity.Token)
        response.enqueue(object: Callback<LkResponse> {

            override fun onFailure(call: Call<LkResponse>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(application, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LkResponse>,
                response: Response<LkResponse>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        balanceName.visibility = View.VISIBLE
                        balanceText.visibility = View.VISIBLE
                        balanceText.text = res.balance
                        usernameText.text = res.username
                        nameText.text = res.name
                    }
                }
            }
        } )
        userCategories.setOnClickListener {
            val newIntent = Intent(this, UsersCategoryActivity::class.java)
            startActivity(newIntent)
        }
        bonusButton.setOnClickListener {

        }
        logoutButton.setOnClickListener {
            MainActivity.Name = ""
            MainActivity.Token = ""
            MainActivity.Role = ""
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }
        statisticButton.setOnClickListener {
            val newIntent = Intent(this, StatisticActivity::class.java)
            startActivity(newIntent)
        }
    }
}