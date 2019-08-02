package com.example.android.shissona


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.android.shissona.database.AppDatabase
import com.example.android.shissona.database.Expense
import com.example.android.shissona.database.ExpenseDao
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_data.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.runOnUiThread
import java.text.NumberFormat


class DataFragment : Fragment() {
    private lateinit var dataSource: ExpenseDao
    private lateinit var list: List<Expense>
    private var totalAmount: Float =0f

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
        (activity as AppCompatActivity).supportActionBar?.show()

        setHasOptionsMenu(true)
        dataSource = AppDatabase.getInstance(container!!.context).expenseDao
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        doAsync {
            list = dataSource.getAll().filter {
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
                    pieEntries.add(PieEntry(it.price.toFloat(), Util.ITEMS[it.expenseType].name))

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
                    centerText = NumberFormat.getCurrencyInstance().format(totalAmount)
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
                    AlertDialog.Builder(this@DataFragment.requireContext())
                        .setMessage(
                            "Description: ${list[position].description}\n Cost: ${NumberFormat.getCurrencyInstance().format(
                                list[position].expensePrice
                            )}"
                        )
                        .setPositiveButton("Done") { p0, p1 -> }.create().show()


                }


            }
        }


    }


    private fun chartList(list: List<Expense>): List<ChartExpense> {
        val arrayHolder = FloatArray(12)
        val newList = ArrayList<ChartExpense>()

        list.forEach {
            when (it.expenseType) {
                0 -> arrayHolder[0] = arrayHolder[0] + it.expensePrice
                1 -> arrayHolder[1] = arrayHolder[1] + it.expensePrice
                2 -> arrayHolder[2] = arrayHolder[2] + it.expensePrice
                3 -> arrayHolder[3] = arrayHolder[3] + it.expensePrice
                4 -> arrayHolder[4] = arrayHolder[4] + it.expensePrice
                5 -> arrayHolder[5] = arrayHolder[5] + it.expensePrice
                6 -> arrayHolder[6] = arrayHolder[6] + it.expensePrice
                7 -> arrayHolder[7] = arrayHolder[7] + it.expensePrice
                8 -> arrayHolder[8] = arrayHolder[8] + it.expensePrice
                9 -> arrayHolder[9] = arrayHolder[9] + it.expensePrice
                10 -> arrayHolder[10] = arrayHolder[10] + it.expensePrice
                11 -> arrayHolder[11] = arrayHolder[11] + it.expensePrice
            }
        }
        arrayHolder.forEachIndexed { index, price ->

            newList.add(ChartExpense(price, index))


        }
        return newList
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.data_overflow_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                AlertDialog.Builder(this@DataFragment.requireContext())
                    .setMessage(getString(R.string.query))
                    .setPositiveButton("Yes") { p0, p1 ->
                        composeAndSendEmail()
                    }.setNegativeButton("Cancel") { p0, p1 ->

                    }
                    .create().show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun composeAndSendEmail() {

        var expenseMessage = ""
        list.forEach {
            expenseMessage += getString(
                R.string.expense,
                Util.getFullDateAndTime(it.entryTime),
                NumberFormat.getCurrencyInstance().format(it.expensePrice),
                Util.ITEMS[it.expenseType].name
            )
        }
        expenseMessage += getString(R.string.monthly_total,NumberFormat.getCurrencyInstance().format(totalAmount))
        expenseMessage += getString(R.string.thank_you)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.email_subject, Util.getMonthAndYear(System.currentTimeMillis()))
        )
        intent.putExtra(Intent.EXTRA_TEXT, expenseMessage)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        }
    }
}
