package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.EditCategoryBody
import kotlinx.android.synthetic.main.activity_edit_admin_category.*
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
        val titleT = findViewById<EditText>(R.id.titleText)
        val priceT = findViewById<EditText>(R.id.priceText)
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
                        titleT.setText(res.title)
                        priceT.setText(res.price.toString())
                        if (res.purchaseRequirement)
                            checkBoxTrue.isChecked = true
                        else
                            checkBoxFalse.isChecked = true
                    }
                }
            }
        })
        editButton.setOnClickListener {
            var purchaseRequirement: Boolean = false
            if (checkBoxTrue.isChecked)
                purchaseRequirement = true
            val editCategoryBody = EditCategoryBody(
                intent.getStringExtra("categoryID").toString().toInt(),
                titleT.text.toString(),
                priceT.text.toString().toInt(),
                purchaseRequirement)
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