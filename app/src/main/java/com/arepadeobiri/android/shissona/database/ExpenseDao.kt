package com.arepadeobiri.android.shissona.database


import androidx.lifecycle.LiveData
import androidx.room.Dao

import androidx.room.Insert

import androidx.room.Query


@Dao

interface ExpenseDao {


    @Insert
    fun insert(vararg expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY entryTime DESC")
    fun getAll(): List<Expense>

    @Query("SELECT * FROM expense_table ORDER BY entryTime DESC")
    fun getReactiveAll(): LiveData<List<Expense>>

    @Query("DELETE FROM expense_table")
    fun clear()




}