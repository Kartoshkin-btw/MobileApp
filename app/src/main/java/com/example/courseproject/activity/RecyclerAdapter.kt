package com.example.courseproject.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.R
import com.example.courseproject.response.CategoryResponse

class RecyclerAdapter (private val list: List<CategoryResponse>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemPrice: TextView = itemView.findViewById(R.id.price)

        init {
            itemView.setOnClickListener {
                    v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_categories, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = list[position].title
        if (list[position].price != 0)
            holder.itemPrice.text = list[position].price.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}