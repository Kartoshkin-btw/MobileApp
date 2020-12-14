package com.example.courseproject.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.body.BonusBody
import com.example.courseproject.body.EditBonusBody
import com.example.courseproject.body.EditQuestionBody
import com.example.courseproject.response.BonusResponse
import kotlinx.android.synthetic.main.activity_bonuses.*
import kotlinx.android.synthetic.main.activity_bonuses.recyclerView
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BonusesActivity : AppCompatActivity(), BonusesAdapter.OnItemClickListener {

    private var list = mutableListOf<BonusResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonuses)
        mainText.text="Награды"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, Profile::class.java)
            startActivity(newIntent)
        }
        if (MainActivity.Role == "Admin")
            createBonus.visibility = View.VISIBLE
        setBonus()
        setList()
        gainBonus.setOnClickListener {
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val response = request.gainBonus(MainActivity.Token)
            response.enqueue(object : Callback<HashMap<String, String>> {

                override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                    Toast.makeText(this@BonusesActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<HashMap<String, String>>,
                    response: Response<HashMap<String, String>>
                ) {
                    val res = response.body()

                    if (response.code() == 200) {
                        if (res != null) {
                            message.text = res.values.first()
                            setBonus()
                        }
                    }
                }
            })
        }
        createBonus.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Создать награду")
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_bonus, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            editText.hint = "Величина награды"
            builder.setView(dialogLayout)
            builder.setPositiveButton("Создать"){dialogInterface, i ->
                val request = Client.buildService(JsonPlaceHolderApi::class.java)
                val bonusBody = BonusBody(editText.text.toString().toInt())
                val response = request.createBonus(MainActivity.Token, bonusBody)
                response.enqueue(object : Callback<Void> {

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@BonusesActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.code() == 200) {
                            setList()
                        }
                    }
                })
            }
            builder.setNegativeButton("Отмена",null)
            builder.show()
        }
    }
    private fun setBonus(){
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getBalance(MainActivity.Token)
        response.enqueue(object: Callback<Int> {

            override fun onFailure(call: Call<Int>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(application, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        balanceName.visibility = View.VISIBLE
                        balanceText.visibility = View.VISIBLE
                        balanceText.text = res.toString()
                    }
                }
            }
        } )
    }
    private fun setList(){
        recyclerView.layoutManager = LinearLayoutManager(application)
        val request = Client.buildService(JsonPlaceHolderApi::class.java)
        val response = request.getBonuses(MainActivity.Token)
        response.enqueue(object: Callback<List<BonusResponse>> {

            override fun onFailure(call: Call<List<BonusResponse>>, t: Throwable){
                Log.i("ITEM", "Failure")
                Toast.makeText(application, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<BonusResponse>>,
                response: Response<List<BonusResponse>>
            ) {
                val res = response.body()

                if(response.code() == 200){
                    if (res != null) {
                        list = res.toMutableList()
                        if (MainActivity.Role == "Admin")
                            recyclerView.adapter = BonusesAdapter(list, this@BonusesActivity)
                        else
                            recyclerView.adapter = BonusesAdapterNoClick(list)
                    }
                }
            }
        } )
    }

    override fun onItemClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Награда")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_bonus, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        editText.hint = "Величина награды"
        editText.setText(list[position].bonusValue)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Изменить"){dialogInterface, i ->
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val editBonusBody = EditBonusBody(list[position].id.toString().toInt(), editText.text.toString().toInt())
            val response = request.editBonus(MainActivity.Token, editBonusBody)
            response.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@BonusesActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.code() == 200) {
                        setList()
                    }
                }
            })
        }
        builder.setNegativeButton("Удалить"){dialogInterface, i ->
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val response = request.deleteBonus(MainActivity.Token, list[position].id.toString().toInt())
            response.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@BonusesActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.code() == 200) {
                        setList()
                    }
                }
            })
        }
        builder.setNeutralButton("Отмена",null)
        builder.show()
    }
}