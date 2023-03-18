package com.example.android.shissona

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.android.shissona.database.Expense
import com.example.android.shissona.databinding.ItemDataListBinding


class ListAdapter(context: Context, list: List<Expense>) : ArrayAdapter<Expense>(context, 0, list) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val currentExpense = getItem(position)

        val newView = ItemDataListBinding.inflate(LayoutInflater.from(context))
        newView.entryDateTextView.text = Util.getFullDateAndTime(currentExpense!!.entryTime)

        newView.itemsImageView.setImageResource(Util.ITEMS[currentExpense.expenseType].imageResource)
        Util.setColorTint(newView.itemsImageView, currentExpense.expenseType)





        return newView.root
    }
}