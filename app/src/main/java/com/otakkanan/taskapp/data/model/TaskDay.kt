package com.otakkanan.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

enum class DayType {
    SPECIFIC_DAY, WEEKDAYS, WEEKENDS, EVERYDAY
}

data class TaskDay(
    val title: String? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val priority: Int? = null,
    var isDone: Boolean = false,
    val subtugas: List<SubTugasTaskDay>? = null,
    val dayType: DayType = DayType.SPECIFIC_DAY,
    val specificDays: List<DayOfWeek>? = null // Updated to list for multiple days
)

@Parcelize
data class SubTugasTaskDay(
    val title: String? = null,
    val isDone: Boolean = false,
): Parcelable
