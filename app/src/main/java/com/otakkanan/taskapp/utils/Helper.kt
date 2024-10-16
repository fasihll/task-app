package com.otakkanan.taskapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kizitonwose.calendarview.model.CalendarMonth
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.data.model.Task
import java.io.InputStreamReader
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

object Helper {

    /**
     * Formatter that is used to format month and year
     */
    private val monthYearFormatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("MMMM yyyy") }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns array of DayOfWeek according to locale order
     * (MUN or SUN) first?
     */
    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
//        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val firstDayOfWeek = DayOfWeek.MONDAY
        val daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek is not DayOfWeek.MONDAY which has ordinal 0.
//        if (firstDayOfWeek != DayOfWeek.MONDAY) {
//            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
//            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
//            return rhs + lhs
//        }
        return daysOfWeek
    }

    /**
     * Returns month and year formatted string in order to display in TextView
     */
    fun formatMonthYear(month: CalendarMonth): String {
        return monthYearFormatter.format(month.yearMonth)
    }

    // Fungsi untuk membaca file JSON dari folder assets
    fun loadJSONFromAssets(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        return reader.readText()
    }

    // Fungsi untuk mengonversi JSON ke List of Goals
    fun parseGoalsJson(jsonString: String): List<Goal> {
        val gson = Gson()
        val goalListType = object : TypeToken<List<Goal>>() {}.type
        return gson.fromJson(jsonString, goalListType)
    }

    fun parseTaskJson(jsonString: String): List<Task> {
        val gson = Gson()
        val taskListType = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(jsonString, taskListType)
    }
}