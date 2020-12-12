package com.example.courseproject.activity.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.R
import com.example.courseproject.response.QuestionResponse

class QuestionsAdapter (
    private val list: List<QuestionResponse>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<QuestionsAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val itemTitle: TextView = itemView.findViewById(R.id.title)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

        override fun onClick(v: View?) {
        }
    }
    interface  OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_categories, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = list[position].text
    }

    override fun getItemCount(): Int {
        return list.size
    }
}