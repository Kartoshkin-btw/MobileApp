package com.example.courseproject.activity.auth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.courseproject.Client
import com.example.courseproject.R
import com.example.courseproject.activity.ListAdapter
import com.example.courseproject.activity.MainActivity
import com.example.courseproject.activity.RecyclerAdapterNoClick
import com.example.courseproject.api.JsonPlaceHolderApi
import com.example.courseproject.response.CategoryResponse
import kotlinx.android.synthetic.main.activity_categories_free.*
import kotlinx.android.synthetic.main.activity_statistic.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.list_categories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class StatisticActivity : AppCompatActivity() {

    private var data1:Long = 0
    private var data2:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        mainText.text="Статистика"
        imageButton.setOnClickListener {
            val newIntent = Intent (this, Profile::class.java)
            startActivity(newIntent)
        }
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        
        setStartData.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val mMonth: Int = month+1
                setStartData.text = dayOfMonth.toString() + "/" + mMonth + "/" + year
                c.set(year, month, dayOfMonth)
                data1 = c.timeInMillis
            }, year, month, day)
            dpd.show()
        }
        setEndData.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val mMonth: Int = month+1
                setEndData.text = dayOfMonth.toString() + "/" + mMonth + "/" + year
                c.set(year, month, dayOfMonth)
                data2 = c.timeInMillis
            }, year, month, day)
            dpd.show()
        }
        searchStat.setOnClickListener {
            val request = Client.buildService(JsonPlaceHolderApi::class.java)
            val response = request.getStatistic(data1, data2)
            response.enqueue(object: Callback<HashMap<String, Int>> {

                override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable){
                    Log.i("ITEM", "Failure")
                    Toast.makeText(this@StatisticActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<HashMap<String, Int>>,
                    response: Response<HashMap<String, Int>>
                ) {
                    val res = response.body()

                    if(response.code() == 200){
                        if (res != null) {
                            var categories = mutableListOf<String>()
                            var counts = mutableListOf<Int>()
                            for(result in res){
                                categories.add(result.key)
                                counts.add(result.value)
                            }
                            val adapter = ListAdapter(this@StatisticActivity, categories, counts)
                            statisticList.adapter = adapter
                        }
                    }
                }
            } )
        }
    }
}