package com.otakkanan.taskapp.ui.main.beranda

import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.TaskDayListBinding

class TaskDayAdapter :  ListAdapter<TaskDay, TaskDayAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TaskDayListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: TaskDayListBinding): RecyclerView.ViewHolder(binding
        .root) {

        init {
            binding.taskCheckbox.setOnClickListener {
                toggleStrikethrough()
            }
        }
        fun bind(items: TaskDay){
            with(binding){
                taskTitle.text = items.title
                taskTime.text = items.time
                if (items.isDone) {
                   setUncheckedState()

                } else {
                    setCheckedState()
                }
            }
        }

        private fun toggleStrikethrough() {
            val isDone = binding.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG != 0
            if (isDone) {
                setCheckedState()
            } else {
                setUncheckedState()
            }
        }

        private fun setCheckedState() {
            binding.taskTitle.paintFlags = binding.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.taskCheckbox.setImageResource(R.drawable.unchecked_task)
            binding.taskTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_onBackground))
            binding.taskTime.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_primary))
            binding.taskTimeContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_theme_primaryFixed))
        }

        private fun setUncheckedState() {
            binding.taskTitle.paintFlags = binding.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskCheckbox.setImageResource(R.drawable.checked_task)
            binding.taskTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_secondary))
            binding.taskTime.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_secondary))
            binding.taskTimeContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.md_theme_secondaryContainer))


        }
    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<TaskDay>(){
            override fun areItemsTheSame(oldItem: TaskDay, newItem: TaskDay): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: TaskDay, newItem: TaskDay): Boolean {
                return oldItem == newItem
            }
        }
    }
}