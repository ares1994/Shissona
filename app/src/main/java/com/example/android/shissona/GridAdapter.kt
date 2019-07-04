package com.example.android.shissona

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
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
        view.itemImage.setImageResource(itemList[position].id)

        when (position) {
            0 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorOne), PorterDuff.Mode.SRC_ATOP)
            1 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorTwo), PorterDuff.Mode.SRC_ATOP)
            2 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorThree), PorterDuff.Mode.SRC_ATOP)
            3 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorFour), PorterDuff.Mode.SRC_ATOP)
            4 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorFive), PorterDuff.Mode.SRC_ATOP)
            5 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorSix), PorterDuff.Mode.SRC_ATOP)
            6 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorSeven), PorterDuff.Mode.SRC_ATOP)
            7 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorEight), PorterDuff.Mode.SRC_ATOP)
            8 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorNine), PorterDuff.Mode.SRC_ATOP)
            9 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorTen), PorterDuff.Mode.SRC_ATOP)
            10 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorEleven), PorterDuff.Mode.SRC_ATOP)
            11 -> view.itemImage.setColorFilter(getColor(view.context, R.color.colorTwelve), PorterDuff.Mode.SRC_ATOP)



        }

        return view
    }
}
