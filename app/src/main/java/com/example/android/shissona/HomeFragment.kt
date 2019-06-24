package com.example.android.shissona


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.shissona.database.AppDatabase
import com.example.android.shissona.database.Expense
import com.example.android.shissona.database.ExpenseDao
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.runOnUiThread


class HomeFragment : Fragment() {

    private lateinit var dataSource: ExpenseDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataSource = AppDatabase.getInstance(container!!.context).expenseDao

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addExpenseButtton.setOnClickListener {
            val expense =
                Expense(null, expenseEditText.text.toString().trim().toInt(), 1, detailsEditText.text.toString())

            doAsync {
                dataSource.insert(expense)
                val list = dataSource.getAll()

                runOnUiThread {
                    list.forEach {
                        Log.d("ares", "expense is ${it.expensePrice} with type ${it.expensePrice}")
                        detailsEditText.setText("")
                        expenseEditText.setText("")
                    }
                }
            }


        }


    }
}
