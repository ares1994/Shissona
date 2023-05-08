package com.arepadeobiri.android.shissona

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat

class Util{

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getMonth(time: Long): String {
            return SimpleDateFormat("MMM")
                .format(time).toString()

        }

        @SuppressLint("SimpleDateFormat")
        fun getMonthAndYear(time: Long): String {
            return SimpleDateFormat("MMM-yyyy")
                .format(time).toString()

        }

        @SuppressLint("SimpleDateFormat")
        fun getFullDateAndTime(time: Long): String {
            return SimpleDateFormat("EEEE, dd-MMM-yyyy' at 'HH:mm")
                .format(time).toString()

        }

        fun setColorTint(view: ImageView, position: Int) {

            when (position) {
                0 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorOne),
                    PorterDuff.Mode.SRC_ATOP
                )
                1 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorTwo),
                    PorterDuff.Mode.SRC_ATOP
                )
                2 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorThree),
                    PorterDuff.Mode.SRC_ATOP
                )
                3 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorFour),
                    PorterDuff.Mode.SRC_ATOP
                )
                4 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorFive),
                    PorterDuff.Mode.SRC_ATOP
                )
                5 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorSix),
                    PorterDuff.Mode.SRC_ATOP
                )
                6 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorSeven),
                    PorterDuff.Mode.SRC_ATOP
                )
                7 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorEight),
                    PorterDuff.Mode.SRC_ATOP
                )
                8 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorNine),
                    PorterDuff.Mode.SRC_ATOP
                )
                9 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorTen),
                    PorterDuff.Mode.SRC_ATOP
                )
                10 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorEleven),
                    PorterDuff.Mode.SRC_ATOP
                )
                11 -> view.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.colorTwelve),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }

        val ITEMS = mutableListOf(
            Item(R.drawable.ic_home_black_24dp, "Home",R.color.colorOne),
            Item(R.drawable.ic_baseline_fastfood_24px_2, "Food",R.color.colorTwo),
            Item(R.drawable.ic_directions_bus_black_24dp, "Transport",R.color.colorThree),
            Item(R.drawable.ic_person_black_24dp, "Personal",R.color.colorFour),
            Item(R.drawable.ic_phone_iphone_black_24dp, "Gadgets",R.color.colorFive),
            Item(R.drawable.ic_directions_car_black_24dp, "Car",R.color.colorSix),
            Item(R.drawable.ic_movie_black_24dp, "Entertainment",R.color.colorSeven),
            Item(R.drawable.ic_location_on_black_24dp, "Travel",R.color.colorEight),
            Item(R.drawable.ic_local_hospital_black_24dp, "Health",R.color.colorNine),
            Item(R.drawable.ic_pets_black_24dp, "Pets",R.color.colorTen),
            Item(R.drawable.ic_card_giftcard_black_24dp, "Gift",R.color.colorEleven),
            Item(R.drawable.ic_receipt_black_24dp, "Bills",R.color.colorTwelve)
        )

        val CHART_COLORS = intArrayOf(
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

    }

}


