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
    val type: Int? = null, //1=task2=kebiasaan
    val evaluasi: List<Evaluasi>? = null,
    val title: String? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val priority: Int? = null,
    val isRepeate: Boolean = false,
    var isDone: Boolean = false,
    val subtugas: List<SubTugasTaskDay>? = null,
    val dayType: DayType = DayType.SPECIFIC_DAY,
    val specificDays: List<DayOfWeek>? = null // Updated to list for multiple days
)

data class Evaluasi(
    val type: Int? = null,//1=yes/no ,2=numerik
    val maxCount: Int? = null
)


@Parcelize
data class SubTugasTaskDay(
    val title: String? = null,
    val isDone: Boolean = false,
): Parcelable


