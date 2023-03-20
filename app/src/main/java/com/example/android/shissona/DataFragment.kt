package com.example.android.shissona


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.shissona.Util.Companion.CHART_COLORS
import com.example.android.shissona.Util.Companion.ITEMS
import com.example.android.shissona.database.Expense
import com.example.android.shissona.databinding.FragmentDataBinding
import com.example.android.shissona.viewModels.DataViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat


class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    private var triggerComposeEmail: (() -> Unit)? = null

    private val viewModel: DataViewModel by viewModels()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDataBinding.bind(view)
        setupObservers()
        setupViews()
    }

    private fun setupViews() {
        binding.composeView.setContent {
            ListView()
        }
    }

    @Composable
    private fun ListView() {
        val list by viewModel.getAllExpensesForTheMonth().collectAsState(initial = emptyList())


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) { _, item ->
                MaterialTheme() {
                    Column(modifier = Modifier.fillMaxWidth().clickable {
                        AlertDialog
                            .Builder(this@DataFragment.requireContext())
                            .setMessage(
                                "Description: ${item.description}\n Cost: ${
                                    NumberFormat
                                        .getCurrencyInstance()
                                        .format(
                                            item.expensePrice
                                        )
                                }"
                            )
                            .setPositiveButton("Done") { _, _ -> composeAndSendEmail(list)}
                            .create()
                            .show()
                    }) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = ITEMS[item.expenseType].imageResource),
                                contentDescription = ITEMS[item.expenseType].name,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp),
                                colorFilter = ColorFilter.tint(colorResource(id = ITEMS[item.expenseType].colorTint))
                            )

                            Text(
                                text = Util.getFullDateAndTime(item.entryTime),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_keyboard_arrow_right_black_24dp),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(colorResource(id = android.R.color.darker_gray))
                            )

                        }

                        Divider(
                            color = colorResource(id = android.R.color.darker_gray),
                            modifier = Modifier.alpha(0.4f)
                        )
                    }
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
                        triggerComposeEmail?.invoke()
                    }.setNegativeButton("Cancel") { p0, p1 ->

                    }
                    .create().show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun setupObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.getAllExpensesForTheMonth().collectLatest { list ->
                try {
                    val pieEntries = ArrayList<PieEntry>()
                    chartList(list).forEach {
                        pieEntries.add(PieEntry(it.price, ITEMS[it.expenseType].name))

                    }

                    triggerComposeEmail={
                        composeAndSendEmail(list)
                    }

                    val dataSet = PieDataSet(pieEntries, "Total Expenses")
                    dataSet.setColors(CHART_COLORS)
                    dataSet.valueTextSize = 14f
                    dataSet.setDrawValues(false)
//                dataSet.sliceSpace = 100f
                    val data = PieData(dataSet)
                    binding.chart.apply {
                        animateY(1000, Easing.EasingOption.EaseInOutCubic)
                        isDrawHoleEnabled = true
                        this.data = data
                        centerText = NumberFormat.getCurrencyInstance()
                            .format(list.map { it.expensePrice }.reduce { acc, expense ->
                                acc + expense
                            })
                        setDrawEntryLabels(false)
                        legend.isEnabled = false
//                    holeRadius = 35f
                        setCenterTextSize(14f)
                        setDescription("")
                    }
                    binding.chart.invalidate()
                    binding.dateTextView.text = Util.getMonthAndYear(System.currentTimeMillis())

                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }


    private fun composeAndSendEmail(list: List<Expense>) {
        Log.d("Ares",list.toString())
        var expenseMessage = ""
        list.forEach {
            expenseMessage += getString(
                R.string.expense,
                Util.getFullDateAndTime(it.entryTime),
                NumberFormat.getCurrencyInstance().format(it.expensePrice),
                Util.ITEMS[it.expenseType].name
            )
        }
        expenseMessage += getString(
            R.string.monthly_total,
            NumberFormat.getCurrencyInstance()
                .format(list.map { it.expensePrice }.reduce { acc, expense ->
                    acc + expense
                })
        )
        expenseMessage += getString(R.string.thank_you)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.email_subject, Util.getMonthAndYear(System.currentTimeMillis()))
        )

        Log.d("Ares",expenseMessage)
        intent.putExtra(Intent.EXTRA_TEXT, expenseMessage)
        Intent.createChooser(intent,"Send Budget Details")
    }
}
