package com.example.android.shissona

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class Util (){

    companion object{

        @SuppressLint("SimpleDateFormat")
        fun getMonth(time: Long): String{
            return SimpleDateFormat("MMM")
                .format(time).toString()

        }

        @SuppressLint("SimpleDateFormat")
        fun getMonthAndYear(time: Long): String{
            return SimpleDateFormat("MMM-yyyy")
                .format(time).toString()

        }
    }

}


