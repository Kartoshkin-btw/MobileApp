package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.CategoryBody
import com.example.courseproject.body.EditCategoryBody
import kotlinx.android.synthetic.main.activity_create_admin_category.*
import kotlinx.android.synthetic.main.activity_create_admin_category.createButton
import kotlinx.android.synthetic.main.activity_create_admin_category.priceText
import kotlinx.android.synthetic.main.activity_create_admin_category.titleText
import kotlinx.android.synthetic.main.activity_edit_admin_category.*
import kotlinx.android.synthetic.main.activity_users_category.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAdminCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_admin_category)
        mainText.text=intent.getStringExtra("categoryName")
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
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getCategory(MainActivity.Token, intent.getStringExtra("categoryID").toString().toInt())
        response.enqueue(object : Callback<EditCategoryBody> {

            override fun onFailure(call: Call<EditCategoryBody>, t: Throwable) {
                Toast.makeText(
                    this@EditAdminCategoryActivity,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<EditCategoryBody>,
                response: Response<EditCategoryBody>
            ) {
                val res = response.body()
                if (response.code() == 200) {
                    if (res != null) {
                        titleText.setText(res.title)
                        priceText.setText(res.price)
                        if (res.purchaseRequirement)
                            checkBoxTrue.isChecked = true
                        else
                            checkBoxFalse.isChecked = false
                    }
                }
            }
        })
        editButton.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(application)
            var purchasedRequirement: Boolean = false
            if (checkBoxTrue.isChecked)
                purchasedRequirement = true
            val editCategoryBody = EditCategoryBody(intent.getStringExtra("categoryID").toString().toInt(), titleText.text.toString(), priceText.text.toString().toInt(), purchasedRequirement)
            val response = request.editCategory(MainActivity.Token, editCategoryBody)
            response.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@EditAdminCategoryActivity,
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