package com.otakkanan.taskapp.utils

import android.graphics.Color

object RandomColor {
    private val colors = listOf(
        Color.parseColor("#F2C75B"),
        Color.parseColor("#61346B")
    )

    fun randomColor(): Int {
        return colors.random()
    }
}