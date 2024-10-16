package com.otakkanan.taskapp.data.repository

import com.otakkanan.taskapp.data.model.SubTugasTaskDay
import com.otakkanan.taskapp.data.model.TaskDay

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class MainRepository {
    companion object {
        val c = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)+1

        private val taskDay = listOf(
            TaskDay(title = "Membuat Dashboard UI", date = LocalDate.of(year, month,21), time=
            LocalTime.of(10,0,
                0),
                isDone =
            true, priority = 2),
            TaskDay(title ="Mengerjakan Kuis",date = LocalDate.of(year, month,21),time= LocalTime.of
                (11,
                0,0),isDone = true),
            TaskDay(title ="Mempelajari Wireframe",date = LocalDate.of(year, month,21),time=
            LocalTime.of
                (13,0,0),
            isDone = false),
            TaskDay(title ="Desain UI",date = LocalDate.of(year, month,21),time= LocalTime.of(17,0,0)
            ,isDone = false,
                subtugas = listOf(
                    SubTugasTaskDay(title = "Membuat moodboard", isDone = true),
                    SubTugasTaskDay(title = "Membuat wireframe", isDone = true),
                    SubTugasTaskDay(title = "Membuat komponen desain", isDone = false),
                )
            ),
            TaskDay(title ="Tugas Laravel",date = LocalDate.of(year, month,21),time= LocalTime.of(17,
                0,0)
            ,isDone = false),


            TaskDay(title ="Mengerjakan Kuis",date = LocalDate.of(year, month,2),time= LocalTime.of
                (11,0,0),isDone = true),
            TaskDay(title ="Mempelajari Wireframe",date = LocalDate.of(year, month,2),time= LocalTime
                .of(13,0,0),
            isDone = false),
            TaskDay(title ="Tugas Laravel",date = LocalDate.of(year, month,2),time= LocalTime.of(17,
                0,0)
            ,isDone = false),


            TaskDay(title ="Mengerjakan Kuis",date = LocalDate.of(year, month,3),time= LocalTime.of
                (11,0,0),isDone = true),
            TaskDay(title ="Mempelajari Wireframe",date = LocalDate.of(year, month,3),time= LocalTime
                .of(13,0,0),
            isDone = false),

            TaskDay(title ="Mengerjakan Kuis",date = LocalDate.of(year, month,17),time= LocalTime.of
                (11,0,0),isDone = true),


        )
    }

    fun getTasksDay(): Flow<List<TaskDay>> {
        return flow { emit(taskDay) }
    }

}