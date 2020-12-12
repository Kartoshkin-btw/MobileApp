package com.example.courseproject.activity.auth

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courseproject.R
import com.example.courseproject.activity.MainActivity
import kotlinx.android.synthetic.main.activity_statistic.*
import kotlinx.android.synthetic.main.appbar.*
import java.util.*

class StatisticActivity : AppCompatActivity() {
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
                setStartData.text = dayOfMonth.toString() + "-" + month + "-" + year
            }, year, month, day)
            dpd.show()
        }
        setEndData.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                setEndData.text = dayOfMonth.toString() + "-" + month + "-" + year
            }, year, month, day)
            dpd.show()
        }
        searchStat.setOnClickListener {

        }
    }
}