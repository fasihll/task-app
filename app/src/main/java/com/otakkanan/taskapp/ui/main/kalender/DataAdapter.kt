package com.otakkanan.taskapp.ui.main.kalender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.CardTaskdayBinding


class DataAdapter(
    diff: DataDiff,
) : ListAdapter<TaskDay, RecyclerView.ViewHolder>(diff) {

    companion object {
        const val TASKDAY = 0x4545
    }

    class TaskDayViewHolder(private val binding: CardTaskdayBinding) : RecyclerView.ViewHolder
        (binding
        .root) {

        fun bind(model: TaskDay) {
            binding.apply {
                nameT.text = model.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskDayViewHolder(CardTaskdayBinding.inflate(inflater,
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskDayViewHolder -> holder.bind(getItem(position) as TaskDay)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TASKDAY
    }

    class DataDiff : DiffUtil.ItemCallback<TaskDay>() {
        override fun areItemsTheSame(oldItem: TaskDay, newItem: TaskDay): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TaskDay, newItem: TaskDay): Boolean {
            return oldItem == newItem
        }
    }
}