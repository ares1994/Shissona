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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
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


        val items = ArrayList<Item>()
        items.add(Item(R.drawable.ic_home_black_24dp, "Home"))
        items.add(Item(R.drawable.ic_baseline_fastfood_24px_2, "Food"))
        items.add(Item(R.drawable.ic_directions_bus_black_24dp, "Transport"))
        items.add(Item(R.drawable.ic_person_black_24dp, "Health"))
        items.add(Item(R.drawable.ic_phone_iphone_black_24dp, "Phone"))
        items.add(Item(R.drawable.ic_directions_car_black_24dp, "Car"))
        items.add(Item(R.drawable.ic_movie_black_24dp, "Entertainment"))


        Log.d("ares", "adapter about to be returned");

        view.itemGridView.apply {
            adapter = GridAdapter(items, this@HomeFragment.requireActivity())
            setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position)

            }
        }


    }


}
