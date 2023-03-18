package com.example.android.shissona

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.shissona.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private lateinit var dataSource: ExpenseDao
    private lateinit var gridAdapter: GridAdapter
    private var actualPosition: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
//        dataSource = AppDatabase.getInstance(container!!.context).expenseDao

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        binding.expenseEditText.filters = arrayOf<InputFilter>(MoneyValueFilter())

        Log.d("ares", "adapter about to be returned")
        gridAdapter = GridAdapter(Util.ITEMS, this@HomeFragment.requireActivity())
        binding.itemGridView.apply {
            adapter = gridAdapter
            setOnItemClickListener { parent, view, position, id ->
                actualPosition = position


            }
        }
        binding.expenseButton.setOnClickListener {
            val detailsText: String = if (binding.detailsEditText.text.isNullOrBlank()) {
                "No details were entered for this expense"
            } else {
                binding.detailsEditText.text.toString().trim()
            }

            if (binding.expenseEditText.text.isNullOrBlank() || actualPosition == -1) {
                Snackbar.make(it, "Enter Expense amount and select category ", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }


            doAsync {
//                dataSource.insert(
//                    Expense(
//                        null,
//                        binding.expenseEditText.text.toString().trim().toFloat(),
//                        actualPosition,
//                        detailsText,
//                        System.currentTimeMillis()
//                    )
//                )
            }
            Snackbar.make(view, "Expense Recorded", Snackbar.LENGTH_SHORT).show()
            binding.expenseEditText.setText("")
            binding.detailsEditText.setText("")

        }

    }


}
