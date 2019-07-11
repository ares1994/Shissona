package com.example.android.shissona

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.item_grid.view.*
import java.text.SimpleDateFormat

class Util() {

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

        fun setColorTint(view: ImageView, position: Int): Unit {

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

        var ITEMS = mutableListOf(
            Item(R.drawable.ic_home_black_24dp, "Home"),
            Item(R.drawable.ic_baseline_fastfood_24px_2, "Food"),
            Item(R.drawable.ic_directions_bus_black_24dp, "Transport"),
            Item(R.drawable.ic_person_black_24dp, "Personal"),
            Item(R.drawable.ic_phone_iphone_black_24dp, "Gadgets"),
            Item(R.drawable.ic_directions_car_black_24dp, "Car"),
            Item(R.drawable.ic_movie_black_24dp, "Entertainment"),
            Item(R.drawable.ic_location_on_black_24dp, "Travel"),
            Item(R.drawable.ic_local_hospital_black_24dp, "Health"),
            Item(R.drawable.ic_pets_black_24dp, "Pets"),
            Item(R.drawable.ic_card_giftcard_black_24dp, "Gift"),
            Item(R.drawable.ic_receipt_black_24dp, "Bills")
        )

    }

}


