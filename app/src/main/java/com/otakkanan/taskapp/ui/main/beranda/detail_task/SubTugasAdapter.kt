package com.otakkanan.taskapp.ui.main.beranda.detail_task

import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.SubTugasListBinding

class SubTugasAdapter :  ListAdapter<TaskDay, SubTugasAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SubTugasListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: SubTugasListBinding): RecyclerView.ViewHolder(binding
        .root) {

        init {
            binding.taskCheckbox.setOnClickListener {
                toggleStrikethrough()
            }
        }
        fun bind(items: TaskDay){
            with(binding){
                taskTitle.text =
                    itemView.context.getString(R.string.task_title_with_bold, itemId.toString(), items
                        .title)
                val itemId = position+1
                val itemsTitle = "Example Title"

                val spannableString = SpannableString("Tugas $itemId — $itemsTitle")

                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    "Tugas $itemId".length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                taskTitle.text = spannableString
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
        }

        private fun setUncheckedState() {
            binding.taskTitle.paintFlags = binding.taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskCheckbox.setImageResource(R.drawable.checked_task_blue)
            binding.taskTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.md_theme_secondary))
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