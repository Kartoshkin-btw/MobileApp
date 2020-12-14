package com.example.courseproject.activity.unauth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.RegistrationBody
import kotlinx.android.synthetic.main.activity_login.log_button
import kotlinx.android.synthetic.main.activity_login.password_text_input
import kotlinx.android.synthetic.main.activity_login.textInputLayout2
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity(val context: Activity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_registration, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        reg_button.setOnClickListener {
            val newIntent = Intent(context, AuthorizationActivity::class.java)

            val password = password_reg.text.toString()
            val username = username_reg.text.toString()
            val name = name_reg.text.toString()

            if (!validateInput(username, password, name)) return@setOnClickListener

            val registrationBody = RegistrationBody(username, password, name)
            val response = request.checkIn(registrationBody)

            response.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.code() == 200) {
                        startActivity(newIntent)
                    } else {
                        error_msg_reg.text = "Данный логин уже существует" + response.code()
                    }
                }
            })
        }
    }
    fun validateInput(username: String, password: String, name: String): Boolean{
        //Проверка имени (Только буквы)
        val pattern: Pattern = Pattern.compile(("^[a-zA-Z ]+$"))
        val matcher: Matcher = pattern.matcher(name)
        val nameIsValid:Boolean = matcher.matches()

        //Пароль (Длина от и до)
        val passwordPattern: Pattern = Pattern.compile("^.{6,12}\$")
        val passwordMatcher: Matcher = passwordPattern.matcher(password)
        val passwordIsValid:Boolean = passwordMatcher.matches()

        //Логин просто не пустой
        val usernameIsValid: Boolean = username.isNotEmpty()



        if(!usernameIsValid) textInputLayout1.hint = "Поле обязательно" else textInputLayout1.hint = "Имя пользователя"
        if(!passwordIsValid) password_text_input.hint = "Длина от 6 до 12" else password_text_input.hint = "Пароль"
        if(!nameIsValid) textInputLayout2.hint = "Поле обязательно и должно содержать только буквы" else textInputLayout2.hint = "Имя"

        if (usernameIsValid && passwordIsValid && nameIsValid)  return true

        return false
    }
}