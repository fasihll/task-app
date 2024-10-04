package com.otakkanan.taskapp.ui.main.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.DayType
import com.otakkanan.taskapp.data.model.TaskDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class TugasBerulangFragment : Fragment(), TugasBerulangAdapter.OnTaskStatusChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TugasBerulangAdapter
    private var allTasks = mutableListOf(
        TaskDay(
            title = "Review project proposal",
            date = LocalDate.now().plusDays(1),
            time = LocalTime.of(9, 30),
            dayType = DayType.SPECIFIC_DAY,
            specificDays = listOf(DayOfWeek.MONDAY)
        ),
        TaskDay(
            title = "Team meeting",
            date = LocalDate.now().plusDays(2),
            time = LocalTime.of(11, 0),
            dayType = DayType.WEEKDAYS
        ),
        TaskDay(
            title = "Weekly report submission",
            date = LocalDate.now().plusDays(3),
            time = LocalTime.of(14, 0),
            dayType = DayType.SPECIFIC_DAY,
            specificDays = listOf(DayOfWeek.WEDNESDAY)
        ),
        TaskDay(
            title = "Client feedback session",
            date = LocalDate.now().plusDays(4),
            time = LocalTime.of(10, 0),
            dayType = DayType.WEEKENDS
        ),
        TaskDay(
            title = "Marketing strategy discussion",
            date = LocalDate.now().plusDays(5),
            time = LocalTime.of(13, 0),
            dayType = DayType.SPECIFIC_DAY,
            specificDays = listOf(DayOfWeek.FRIDAY)
        ),
        TaskDay(
            title = "Design review meeting",
            date = LocalDate.now().plusDays(6),
            time = LocalTime.of(15, 30),
            dayType = DayType.WEEKDAYS
        ),
        TaskDay(
            title = "Code review session",
            date = LocalDate.now().plusDays(7),
            time = LocalTime.of(16, 0),
            dayType = DayType.SPECIFIC_DAY,
            specificDays = listOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY)
        ),
        TaskDay(
            title = "Budget planning",
            date = LocalDate.now().plusDays(8),
            time = LocalTime.of(12, 0),
            dayType = DayType.EVERYDAY
        ),
        TaskDay(
            title = "Team building activity",
            date = LocalDate.now().plusDays(9),
            time = LocalTime.of(17, 30),
            dayType = DayType.SPECIFIC_DAY,
            specificDays = listOf(DayOfWeek.SATURDAY)
        ),
        TaskDay(
            title = "Submit timesheet",
            date = LocalDate.now().plusDays(10),
            time = LocalTime.of(18, 0),
            dayType = DayType.WEEKENDS
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_tugas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TugasBerulangAdapter(allTasks, this)
        recyclerView.adapter = adapter

        // Display all tasks, regardless of priority or status
        adapter.updateTasks(allTasks)

        return view
    }

    override fun onTaskStatusChanged(task: TaskDay) {
        // Update the task in the list
        val taskIndex = allTasks.indexOfFirst { it.title == task.title }
        if (taskIndex != -1) {
            allTasks[taskIndex] = task
            adapter.updateTasks(allTasks)
        }
    }
}
