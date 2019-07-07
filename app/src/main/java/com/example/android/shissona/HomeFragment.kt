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


        val items = ArrayList<Item>()
        items.add(Item(R.drawable.ic_home_black_24dp, "Home"))
        items.add(Item(R.drawable.ic_baseline_fastfood_24px_2, "Food"))
        items.add(Item(R.drawable.ic_directions_bus_black_24dp, "Transport"))
        items.add(Item(R.drawable.ic_person_black_24dp, "Personal"))
        items.add(Item(R.drawable.ic_phone_iphone_black_24dp, "Gadgets"))
        items.add(Item(R.drawable.ic_directions_car_black_24dp, "Car"))
        items.add(Item(R.drawable.ic_movie_black_24dp, "Entertainment"))
        items.add(Item(R.drawable.ic_location_on_black_24dp, "Travel"))
        items.add(Item(R.drawable.ic_local_hospital_black_24dp, "Health"))
        items.add(Item(R.drawable.ic_pets_black_24dp, "Pets"))
        items.add(Item(R.drawable.ic_card_giftcard_black_24dp, "Gift"))
        items.add(Item(R.drawable.ic_receipt_black_24dp, "Bills"))


        Log.d("ares", "adapter about to be returned");
        gridAdapter = GridAdapter(items, this@HomeFragment.requireActivity())
        view.itemGridView.apply {
            adapter = gridAdapter
            setOnItemClickListener { parent, view, position, id ->
                actualPosition = position


            }
        }
        view.expenseButton.setOnClickListener {

            doAsync {
                dataSource.insert(
                    Expense(
                        null,
                        expenseEditText.text.toString().trim().toInt(),
                        actualPosition,
                        detailsEditText.text.toString().trim(),
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
