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
    val target: List<Target>? = null,
    val riwayat: List<Riwayat>? = null
): Parcelable

@Parcelize
data class Target(
    val name: String? = null,
    val targetType: Int? = null,
    val startValue: Int? = null,
    val endValue: Int? = null,
    val progress: Int? = null,
): Parcelable

@Parcelize
data class Riwayat(
    val targetName: String? = null,
    val amount: Int? = null,
    val created_at: String? = null,
): Parcelable