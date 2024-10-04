package com.otakkanan.taskapp.ui.main.tugas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.DayType
import com.otakkanan.taskapp.data.model.TaskDay
import java.time.format.TextStyle
import java.util.*

class TugasBerulangAdapter(
    private var tasks: List<TaskDay>,
    private val taskStatusChangeListener: OnTaskStatusChangeListener
) : RecyclerView.Adapter<TugasBerulangAdapter.TaskViewHolder>() {

    interface OnTaskStatusChangeListener {
        fun onTaskStatusChanged(task: TaskDay)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconDone: ImageView = itemView.findViewById(R.id.icon_done)
        val title: TextView = itemView.findViewById(R.id.title)
        val badgeTime: TextView = itemView.findViewById(R.id.day_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tugas_berulang, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title

        // Update the icon based on the current task status
        holder.iconDone.setImageResource(
            if (task.isDone) R.drawable.ic_repeat_on else R.drawable.ic_repeat_off
        )

        // Display the correct day type, including multiple specific days
        holder.badgeTime.text = when (task.dayType) {
            DayType.SPECIFIC_DAY -> {
                task.specificDays?.joinToString(", ") {
                    it.getDisplayName(TextStyle.FULL, Locale.getDefault())
                } ?: ""
            }
            DayType.WEEKDAYS -> "Weekdays (Mon-Fri)"
            DayType.WEEKENDS -> "Weekends (Sat-Sun)"
            DayType.EVERYDAY -> "Every day"
        }

        // Set up the click listener to toggle the task status
        holder.iconDone.setOnClickListener {
            val updatedTask = task.copy(isDone = !task.isDone)
            taskStatusChangeListener.onTaskStatusChanged(updatedTask)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<TaskDay>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
