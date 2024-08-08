package com.otakkanan.taskapp.data.model

data class Task(
    val title: String,
    val progress: Int,
    val team: List<Team>,
)

data class Team(
    val name: String,
    val image: String,
)
