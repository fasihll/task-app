package com.otakkanan.taskapp.ui.main.beranda.tugas_harian

import android.content.res.Resources.Theme
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R
import com.google.android.material.R as Rmat
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

        private var taskDay: TaskDay? = null
        private var isCurrentlyChecked: Boolean = false

        init {
            binding.taskCheckbox.setOnClickListener {
                toggleStrikethrough(!isCurrentlyChecked) // Toggle berdasarkan status terbaru
            }
        }
        fun bind(items: TaskDay){

            taskDay = items

            with(binding){
                taskTitle.text = items.title
                taskTime.text = items.time.toString()

                if (items.isRepeate) {
                    repeateContainer.visibility = View.VISIBLE
                }

                if (taskTime.compoundDrawables.getOrNull(0) == null) {
                    taskTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bell, 0, 0, 0)
                }

                if (iconRepeate.compoundDrawables.getOrNull(0) == null) {
                    iconRepeate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ph_repeat_fill, 0, 0, 0)
                }

                if (items.isDone) {
                    setUncheckedState()
                } else {
                    setCheckedState()
                }
            }
        }

        private fun toggleStrikethrough(checked: Boolean) {
            if (checked != isCurrentlyChecked) { // Hanya lakukan toggle jika status berubah
                isCurrentlyChecked = checked
                if (checked) {
                    setCheckedState()
                } else {
                    setUncheckedState()
                }
            }
        }

        private fun setCheckedState() {
            var theme: Theme = itemView.context.getTheme()
            var tV = TypedValue()

//            binding.taskTitle.paintFlags = binding.taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.taskCheckbox.setImageResource(R.drawable.unchecked_task)
            binding.taskTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_onBackground))

            var colorPrimaryContainer = theme.resolveAttribute(Rmat.attr.colorPrimaryContainer, tV,
                true)
            var taskTime: Int = tV.data
            binding.taskTime.setTextColor(taskTime)
            binding.taskTime.compoundDrawables.getOrNull(0)?.setTint(taskTime)
            binding.iconRepeate.compoundDrawables.getOrNull(0)?.setTint(taskTime)
            var colorPrimaryFixed = theme.resolveAttribute(Rmat.attr.colorPrimaryFixed, tV,
                true)
            var taskTimeContainer: Int = tV.data
            binding.taskTimeContainer.setCardBackgroundColor(taskTimeContainer)
            binding.repeateContainer.setCardBackgroundColor(taskTimeContainer)


        }

        private fun setUncheckedState() {
            var theme: Theme = itemView.context.getTheme()
            var tV = TypedValue()

//            binding.taskTitle.paintFlags = binding.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskCheckbox.setImageResource(R.drawable.checked_task)
            binding.taskTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_secondary))

            var colorSecondary = theme.resolveAttribute(Rmat.attr.colorOnSecondaryFixedVariant, tV,
                true)
            var taskTimeColor: Int = tV.data
            binding.taskTime.setTextColor(taskTimeColor)
            binding.taskTime.compoundDrawables.getOrNull(0)?.setTint(taskTimeColor)
            binding.iconRepeate.compoundDrawables.getOrNull(0)?.setTint(taskTimeColor)


            var colorOnSecondary = theme.resolveAttribute(Rmat.attr.colorSecondaryContainer, tV,
                true)
            var containerColor: Int = tV.data
            binding.taskTimeContainer.setCardBackgroundColor(containerColor)
            binding.repeateContainer.setCardBackgroundColor(containerColor)

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