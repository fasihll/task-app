package com.otakkanan.taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title: String? = null,
    val manajer: List<Manajer>? = null,
    val deadline: String? = null,
    val deskripsi: String? = null,
    val progress: Int? = null,
    val team: List<Team>? = null,
    val subtugas: List<SubTugas>? = null
): Parcelable
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
    val time: String? = null,
    val isDone: Boolean? = null
): Parcelable