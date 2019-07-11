package com.example.android.shissona


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.shissona.database.AppDatabase
import com.example.android.shissona.database.Expense
import com.example.android.shissona.database.ExpenseDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.runOnUiThread


class HomeFragment : Fragment() {

    private lateinit var dataSource: ExpenseDao
    private lateinit var gridAdapter: GridAdapter
    private var actualPosition: Int = -1
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

        Log.d("ares", "adapter about to be returned");
        gridAdapter = GridAdapter(Util.ITEMS, this@HomeFragment.requireActivity())
        view.itemGridView.apply {
            adapter = gridAdapter
            setOnItemClickListener { parent, view, position, id ->
                actualPosition = position


            }
        }
        view.expenseButton.setOnClickListener {
            val detailsText: String = if (detailsEditText.text.isNullOrBlank()) {
                "No details were entered for this expense"
            } else {
                detailsEditText.text.toString().trim()
            }

            if (expenseEditText.text.isNullOrBlank() || actualPosition == -1) {
                Snackbar.make(it, "Enter Expense amount and select category ", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }


            doAsync {
                dataSource.insert(
                    Expense(
                        null,
                        expenseEditText.text.toString().trim().toInt(),
                        actualPosition,
                        detailsText,
                        System.currentTimeMillis()
                    )
                )
            }
            Snackbar.make(view, "Expense Recorded", Snackbar.LENGTH_SHORT).show()
            expenseEditText.setText("")
            detailsEditText.setText("")

        }

    }


}
