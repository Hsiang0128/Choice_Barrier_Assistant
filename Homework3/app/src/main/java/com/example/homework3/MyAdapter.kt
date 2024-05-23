package com.example.homework3

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, data: ArrayList<Item>, private val layout: Int) : ArrayAdapter<Item>(context, layout, data) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        view.findViewById<ImageView>(R.id.img_photo).setImageResource(item.photo)
        view.findViewById<TextView>(R.id.tv_msg).text = item.name

        return view
    }
}