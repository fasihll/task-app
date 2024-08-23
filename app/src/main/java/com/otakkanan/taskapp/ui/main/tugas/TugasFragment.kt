package com.otakkanan.taskapp.ui.main.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.TaskDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class TugasFragment : Fragment(), TugasAdapter.OnTaskStatusChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TugasAdapter
    private var allTasks = mutableListOf(
        TaskDay(title = "Task 1", date = LocalDate.now(), time = LocalTime.of(10, 0), priority = 1, isDone = false),
        TaskDay(title = "Task 2", date = LocalDate.now().plusDays(1), time = LocalTime.of(14, 0), priority = 3, isDone = true),
        TaskDay(title = "Task 3", date = LocalDate.now().plusDays(2), time = LocalTime.of(9, 0), priority = 0, isDone = false),
        TaskDay(title = "Task 4", date = LocalDate.now().plusDays(3), time = LocalTime.of(11, 0), priority = 0, isDone = true),
        TaskDay(title = "Task 5", date = LocalDate.now().plusDays(4), time = LocalTime.of(13, 0), priority = 2, isDone = false),
        TaskDay(title = "Task 6", date = LocalDate.now().plusDays(5), time = LocalTime.of(15, 0), priority = 3, isDone = true),
        TaskDay(title = "Task 7", date = LocalDate.now().plusDays(6), time = LocalTime.of(16, 0), priority = 0, isDone = false),
        TaskDay(title = "Task 8", date = LocalDate.now().plusDays(7), time = LocalTime.of(17, 0), priority = 2, isDone = true),
        TaskDay(title = "Task 9", date = LocalDate.now().plusDays(8), time = LocalTime.of(18, 0), priority = 3, isDone = false),
        TaskDay(title = "Task 10", date = LocalDate.now().plusDays(9), time = LocalTime.of(19, 0), priority = 1, isDone = true)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_tugas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TugasAdapter(allTasks, this)
        recyclerView.adapter = adapter

        // Display all tasks, regardless of priority or status
        adapter.updateTasks(allTasks)

        // Implement swipe-to-dismiss for task deletion
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // No move operation needed, return false
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskToRemove = allTasks[position]

                // Remove the task from the list
                allTasks.removeAt(position)
                adapter.notifyItemRemoved(position)

                // Optionally, show an undo option using Snackbar
                Snackbar.make(recyclerView, "${taskToRemove.title} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        // Reinsert the task if the user undoes the delete
                        allTasks.add(position, taskToRemove)
                        adapter.notifyItemInserted(position)
                    }.show()
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)

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
