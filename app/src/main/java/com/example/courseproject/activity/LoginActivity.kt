package com.example.courseproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.AuthenticateBody
import com.example.courseproject.response.AuthenticationResponse
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mainText.text="Вход"

        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        imageButton.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            startActivity(newIntent)
        }

        reg_button.setOnClickListener {
            val newIntent = Intent (this, RegistrationActivity::class.java)
            startActivity(newIntent)
        }

        log_button.setOnClickListener {
            val newIntent = Intent (this, MainActivity::class.java)
            val password = password_auth.text.toString()
            val username = username_auth.text.toString()
            val authenticationBody =  AuthenticateBody(username, password)
            val response = request.authenticate(authenticationBody)

            response.enqueue(object: Callback<AuthenticationResponse> {

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable){
                    Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    val res = response.body()
                    if(response.code() == 200 && res != null){
                        newIntent.apply {
                            putExtra(MainActivity.Token, res.token)
                        }
                        startActivity(newIntent)
                    } else {
                        error_msg_auth.text = "Неверное имя пользователя или пароль"
                    }
                }
            })
        }
    }
}