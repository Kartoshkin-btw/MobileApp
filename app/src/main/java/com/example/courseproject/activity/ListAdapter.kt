package com.example.courseproject.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.example.courseproject.R

class ListAdapter(private val context: Activity, private val title: Array<String>, private val price: Array<String>): ArrayAdapter<String>(context, R.layout.list_item, title) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val priceText = rowView.findViewById(R.id.price) as TextView
        titleText.text = title[position]
        priceText.text = price[position]

        return rowView
    }
}