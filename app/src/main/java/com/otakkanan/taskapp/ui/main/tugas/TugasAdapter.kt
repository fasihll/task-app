package com.otakkanan.taskapp.ui.main.tugas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.TaskDay

class TugasAdapter(
    private var tasks: List<TaskDay>,
    private val taskStatusChangeListener: OnTaskStatusChangeListener
) : RecyclerView.Adapter<TugasAdapter.TaskViewHolder>() {

    interface OnTaskStatusChangeListener {
        fun onTaskStatusChanged(task: TaskDay)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconDone: ImageView = itemView.findViewById(R.id.icon_done)
        val title: TextView = itemView.findViewById(R.id.title)
        val badgeTime: TextView = itemView.findViewById(R.id.time)
        val badgePriority: TextView = itemView.findViewById(R.id.priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tugas, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.badgeTime.text = task.time?.toString() ?: ""
        holder.badgePriority.text = task.priority?.toString() ?: ""

        // Update the icon based on the current task status
        holder.iconDone.setImageResource(
            if (task.isDone) R.drawable.ic_checked else R.drawable.ic_unchecked
        )

        // Set the visibility of the priority badge
        if (task.priority == 0) {
            holder.itemView.findViewById<MaterialCardView>(R.id.priority_container).visibility = View.GONE
        } else {
            holder.itemView.findViewById<MaterialCardView>(R.id.priority_container).visibility = View.VISIBLE
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
