package com.example.android.shissona

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Item(
    @DrawableRes
    val imageResource: Int,
    val name: String,
    @ColorRes
    val colorTint:Int,
)