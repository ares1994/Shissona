package com.arepadeobiri.android.shissona.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import com.arepadeobiri.android.shissona.database.AppDatabase
import com.arepadeobiri.android.shissona.database.Expense
import com.arepadeobiri.android.shissona.database.ExpenseDao
import kotlinx.coroutines.flow.Flow

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: ExpenseDao by lazy {
        AppDatabase.getInstance(getApplication()).expenseDao
    }


    fun getAllExpensesForTheMonth(): Flow<List<Expense>> {
        return dao.getReactiveAll().asFlow()
    }


}