package com.otakkanan.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Task(
    val title: String? = null,
    val manajer: List<Manajer>? = null,
    val deadline: List<Deadline>? = null,
    val priority: Int?= null,
    val deskripsi: String? = null,
    val progress: Int? = null,
    val team: List<Team>? = null,
    val subtugas: List<SubTugas>? = null
): Parcelable
@Parcelize
data class Deadline(
    val date: LocalDate? = null,
    val time: LocalTime? = null
):Parcelable


@Parcelize
data class Manajer(
    val name: String? = null,
    val image: String? = null
): Parcelable

@Parcelize
data class Team(
    val name: String? = null,
    val image: String? = null,
    val role: String? = null
): Parcelable

@Parcelize
data class SubTugas(
    val title: String? = null,
    val deadline: List<Deadline>? = null,
    val isDone: Boolean? = null
): Parcelable