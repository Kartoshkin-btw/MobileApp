package com.example.courseproject.activity

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.courseproject.R

class ListAdapter(private val context: Activity, private val categories: List<String>, private val count: List<Int>): ArrayAdapter<String>(context, R.layout.list_item, categories) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item, null, true)

        val categoryText = rowView.findViewById(R.id.categoryText) as TextView
        val countText = rowView.findViewById(R.id.countText) as TextView
        categoryText.text = categories[position]
        countText.text = count[position].toString()

        return rowView
    }
}