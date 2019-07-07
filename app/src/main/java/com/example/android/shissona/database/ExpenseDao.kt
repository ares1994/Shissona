package com.example.android.shissona.database


import androidx.room.Dao

import androidx.room.Delete

import androidx.room.Insert

import androidx.room.Query


@Dao

interface ExpenseDao {


    @Insert
    fun insert(vararg expense: Expense)

    @Query("SELECT * FROM expense_table")
    fun getAll(): List<Expense>

    @Query("DELETE FROM expense_table")
    fun clear()
}