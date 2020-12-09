package com.example.courseproject.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.courseproject.R
import kotlinx.android.synthetic.main.activity_categories_all.*

class CategoriesAllActivity : Fragment() {
    var categories = arrayOf("123","234","345","345","345","345","345","345","345","345","345","345","345","345","345","345","345","345","345","345")
    var price = arrayOf("100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100","100")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_categories_all, container, false)
        //val listAdapter = ListAdapter(this,categories,price)
        //val adapter = ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, categories)
        //categoriesList.adapter = listAdapter
    }
}