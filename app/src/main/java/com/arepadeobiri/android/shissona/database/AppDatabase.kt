package com.arepadeobiri.android.shissona.database


import android.content.Context

import androidx.room.Database

import androidx.room.Room

import androidx.room.RoomDatabase


@Database(entities = [Expense::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {


    abstract val expenseDao: ExpenseDao


    companion object {

        @Volatile

        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {


            synchronized(this) {


                var instance = INSTANCE

                // If instance is `null` make a new database instance.

                if (instance == null) {

                    instance = Room.databaseBuilder(

                        context.applicationContext,

                        AppDatabase::class.java,

                        "expense_table"

                    )


                        .fallbackToDestructiveMigration()

                        .build()



                    INSTANCE = instance

                }

                return instance

            }

        }

    }


}