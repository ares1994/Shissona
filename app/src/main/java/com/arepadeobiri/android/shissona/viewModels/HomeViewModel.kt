package com.arepadeobiri.android.shissona.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arepadeobiri.android.shissona.database.AppDatabase
import com.arepadeobiri.android.shissona.database.Expense
import com.arepadeobiri.android.shissona.database.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> get() = _amount

    private val _details = MutableStateFlow("")
    val details: StateFlow<String> get() = _details

    private val _selectedIndex = MutableStateFlow(-1)
    val selectedIndex: StateFlow<Int> get() = _selectedIndex


    private val dao: ExpenseDao by lazy {
        AppDatabase.getInstance(getApplication()).expenseDao
    }

    fun setDetails(value: String) {
        _details.value = value
    }


    fun setSelectedIndex(value: Int) {
        _selectedIndex.value = value
    }


    fun setAmount(value: String) {
        _amount.value = value
    }

    fun insert() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.insert(
                    Expense(
                        null,
                        amount.value.toFloat(),
                        selectedIndex.value,
                        details.value,
                        System.currentTimeMillis()
                    )
                )

                _details.value = ""
                _selectedIndex.value = -1
                _amount.value = ""
            }
        }
    }


}