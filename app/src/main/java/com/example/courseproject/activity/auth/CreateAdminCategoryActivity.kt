package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.CategoryBody
import kotlinx.android.synthetic.main.activity_create_admin_category.*
import kotlinx.android.synthetic.main.activity_create_admin_category.createButton
import kotlinx.android.synthetic.main.activity_users_category.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAdminCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_admin_category)
        mainText.text="Новая категория"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, UsersCategoryActivity::class.java)
            startActivity(newIntent)
        }
        val checkBoxFalse = findViewById<CheckBox>(R.id.purchaseRequirementFalse)
        val checkBoxTrue = findViewById<CheckBox>(R.id.purchaseRequirementTrue)
        checkBoxFalse.setOnClickListener{
            checkBoxTrue.isChecked = false
        }
        checkBoxTrue.setOnClickListener{
            checkBoxFalse.isChecked = false
        }
        createButton.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(application)
            var purchasedRequirement: Boolean = false
            if (checkBoxTrue.isChecked)
                purchasedRequirement = true
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val categoryBody = CategoryBody(titleText.text.toString(), priceText.text.toString().toInt(), purchasedRequirement)
            val response = request.createAdminCategory(MainActivity.Token, categoryBody)
            response.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@CreateAdminCategoryActivity,
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
            val newIntent = Intent (this, UsersCategoryActivity::class.java)
            startActivity(newIntent)
        }
    }
}