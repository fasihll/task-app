package com.otakkanan.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Goal(
    val name: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val isPengingat: Boolean? = false,
    val priority: Int?= null,
    val progress: Int? = null,
    val team: List<Team>? = null,
    val target: List<Target>? = null
): Parcelable

@Parcelize
data class Target(
    val name: String? = null,
    val targetType: Int? = null,
    val startValue: Int? = null,
    val endValue: Int? = null,
    val progress: Int? = null,
): Parcelable