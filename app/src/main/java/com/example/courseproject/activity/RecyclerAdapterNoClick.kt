package com.example.courseproject.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.R
import com.example.courseproject.response.CategoryResponse

class RecyclerAdapterNoClick (private val isPrice: Boolean, private val list: List<CategoryResponse>): RecyclerView.Adapter<RecyclerAdapterNoClick.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemPrice: TextView = itemView.findViewById(R.id.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_categories, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterNoClick.ViewHolder, position: Int) {
        holder.itemTitle.text = list[position].title
        if (isPrice)
            holder.itemPrice.text = list[position].price.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}