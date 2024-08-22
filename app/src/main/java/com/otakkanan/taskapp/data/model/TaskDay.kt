package com.otakkanan.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

data class TaskDay(
    val title: String? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val priority: Int? = null,
    var isDone: Boolean = false,
    val subtugas: List<SubTugasTaskDay>? = null
)

@Parcelize
data class SubTugasTaskDay(
    val title: String? = null,
    val isDone: Boolean = false,
): Parcelable
