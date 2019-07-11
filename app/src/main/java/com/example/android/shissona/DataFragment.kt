package com.example.android.shissona


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.shissona.database.AppDatabase
import com.example.android.shissona.database.Expense
import com.example.android.shissona.database.ExpenseDao
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_data.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.runOnUiThread


class DataFragment : Fragment() {
    private lateinit var dataSource: ExpenseDao

    private val CHART_COLORS = intArrayOf(
        Color.rgb(244, 67, 54),
        Color.rgb(103, 58, 183),
        Color.rgb(63, 81, 181),
        Color.rgb(76, 175, 80),
        Color.rgb(255, 193, 7),
        Color.rgb(0, 150, 136),
        Color.rgb(233, 30, 99),
        Color.rgb(33, 150, 243),
        Color.rgb(213, 29, 29),
        Color.rgb(205, 220, 57),
        Color.rgb(156, 39, 176),
        Color.rgb(233, 118, 30)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataSource = AppDatabase.getInstance(container!!.context).expenseDao
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalAmount = 0

        doAsync {
            val list = dataSource.getAll().filter {
                Util.getMonthAndYear(it.entryTime) == Util.getMonthAndYear(System.currentTimeMillis())
            }
//            val displayList = list.filter {
//                Util.getMonthAndYear(it.entryTime) == Util.getMonthAndYear(System.currentTimeMillis())
//            }
            runOnUiThread {
                list.forEach {
                    totalAmount += it.expensePrice
                }
                val pieEntries = ArrayList<PieEntry>()
                chartList(list).forEach {
                    pieEntries.add(PieEntry(it.price.toFloat(), tagEquivalent(it.expenseType)))

                }

                val dataSet = PieDataSet(pieEntries, "Total Expenses")
                dataSet.setColors(CHART_COLORS)
                dataSet.valueTextSize = 14f
                dataSet.setDrawValues(false)
//                dataSet.sliceSpace = 100f
                val data = PieData(dataSet)
                view.chart.apply {
                    animateY(1000, Easing.EasingOption.EaseInOutCubic)
                    isDrawHoleEnabled = true
                    this.data = data
                    centerText = "₦$totalAmount"
                    setDrawEntryLabels(false)
                    legend.isEnabled = false
//                    holeRadius = 35f
                    setCenterTextSize(14f)
                    setDescription("")
                }
//                view.chart.setExtraOffsets(5f, 5f, 10f, 5f)
                view.chart.invalidate()
                view.dateTextView.text = Util.getMonthAndYear(System.currentTimeMillis())

                view.dataListView.adapter = ListAdapter(this@DataFragment.requireActivity(), list)
                view.dataListView.setOnItemClickListener { parent, view, position, id ->
                    AlertDialog.Builder(this@DataFragment.requireContext()).setMessage(list[position].description)
                        .setPositiveButton("Done") { p0, p1 -> }.create().show()


                }


            }
        }


    }

    private fun tagEquivalent(type: Int): String {
        var tag = ""
        when (type) {
            0 -> tag = "Home"
            1 -> tag = "Food"
            2 -> tag = "Transport"
            3 -> tag = "Personal"
            4 -> tag = "Gadgets"
            5 -> tag = "Car"
            6 -> tag = "Entertainment"
            7 -> tag = "Travel"
            8 -> tag = "Health"
            9 -> tag = "Pets"
            10 -> tag = "Gifts"
            11 -> tag = "Bills"

        }

        return tag
    }

    private fun chartList(list: List<Expense>): List<ChartExpense> {
        val arrayHolder = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val newList = ArrayList<ChartExpense>()

        list.forEach {
            when (it.expenseType) {
                0 -> arrayHolder[0] += it.expensePrice
                1 -> arrayHolder[1] += it.expensePrice
                2 -> arrayHolder[2] += it.expensePrice
                3 -> arrayHolder[3] += it.expensePrice
                4 -> arrayHolder[4] += it.expensePrice
                5 -> arrayHolder[5] += it.expensePrice
                6 -> arrayHolder[6] += it.expensePrice
                7 -> arrayHolder[7] += it.expensePrice
                8 -> arrayHolder[8] += it.expensePrice
                9 -> arrayHolder[9] += it.expensePrice
                10 -> arrayHolder[10] += it.expensePrice
                11 -> arrayHolder[11] += it.expensePrice
            }
        }
        arrayHolder.forEachIndexed { index, price ->

            newList.add(ChartExpense(price, index))


        }
        return newList
    }

}
