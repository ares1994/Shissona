package com.example.android.shissona.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_table")
data class Expense(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,

    @ColumnInfo
    val expensePrice: Int,

    @ColumnInfo
    val expenseType: Int,

    @ColumnInfo
    val description: String
)