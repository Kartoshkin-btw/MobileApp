package com.example.courseproject.activity.unauth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.RecyclerAdapterNoClick
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.AuthenticateBody
import com.example.courseproject.response.AuthenticationResponse
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_free.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity(val context: Activity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_login, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log_button.setOnClickListener {
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val newIntent = Intent(context, MainActivity::class.java)
            val password = password_auth.text.toString()
            val username = username_auth.text.toString()
            val authenticationBody = AuthenticateBody(username, password)
            val response = request.authenticate(authenticationBody)

            response.enqueue(object : Callback<AuthenticationResponse> {

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        MainActivity.Token = "Bearer " + res.token
                        MainActivity.Name = res.name.toString()
                        if (res.admin)
                            MainActivity.Role = "Admin"
                        else
                            MainActivity.Role = "User"
                        startActivity(newIntent)
                    } else {
                        error_msg_auth.text = "Неверное имя пользователя или пароль"
                    }
                }
            })
        }
    }
}