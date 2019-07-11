package com.example.android.shissona

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_grid.view.*

data class GridAdapter(val itemList: List<Item>, val activity: Activity) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(activity, R.layout.item_grid, null)
        view.itemText.text = itemList[position].name
        view.itemImage.setImageResource(itemList[position].imageResource)

        Util.setColorTint(view.itemImage, position)

        return view
    }
}
