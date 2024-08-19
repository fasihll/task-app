package com.otakkanan.taskapp.ui.main.kalender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.SubTugasTaskDay
import com.otakkanan.taskapp.databinding.SubTugasCalendarListItemBinding

class SubTaskCalendarAdapter :  ListAdapter<SubTugasTaskDay, SubTaskCalendarAdapter.MyViewHolder>
    (DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SubTugasCalendarListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: SubTugasCalendarListItemBinding): RecyclerView.ViewHolder(binding
        .root) {

        fun bind(items: SubTugasTaskDay){
            with(binding){
                subtaskCheckbox.text = items.title
                subtaskCheckbox.isChecked= items.isDone
            }
        }
    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<SubTugasTaskDay>(){
            override fun areItemsTheSame(oldItem: SubTugasTaskDay, newItem: SubTugasTaskDay): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: SubTugasTaskDay, newItem: SubTugasTaskDay): Boolean {
                return oldItem == newItem
            }
        }
    }
}