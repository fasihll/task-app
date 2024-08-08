package com.otakkanan.taskapp.ui.main.beranda

import android.graphics.BlendMode
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.TasksBannerListBinding
import com.otakkanan.taskapp.utils.RandomColor.randomColor

class TaskAdapter :  ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TasksBannerListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: TasksBannerListBinding): RecyclerView.ViewHolder(binding
        .root) {


        fun bind(items: Task){
            with(binding){

                binding.tasksCard.setCardBackgroundColor(randomColor())
                binding.tasksCard.backgroundTintMode = PorterDuff.Mode.SRC_ATOP

                taskTitle.text = items.title
                progresBar.progress = items.progress
                when(items.team.size){
                    1 -> {
                        teamImage1.visibility = View.VISIBLE
                        Glide.with(itemView.context)
                            .load(items.team[0].image)
                            .into(teamImage1)
                    }
                    2 -> {
                        teamImage1.visibility = View.VISIBLE
                        teamImage2.visibility = View.VISIBLE
                        Glide.with(itemView.context)
                            .load(items.team[0].image)
                            .into(teamImage1)
                        Glide.with(itemView.context)
                            .load(items.team[1].image)
                            .into(teamImage2)
                    }
                    else -> {
                        teamImage1.visibility = View.VISIBLE
                        teamImage2.visibility = View.VISIBLE
                        totalTeamCotntainer.visibility  = View.VISIBLE
                        Glide.with(itemView.context)
                            .load(items.team[0].image)
                            .into(teamImage1)
                        Glide.with(itemView.context)
                            .load(items.team[1].image)
                            .into(teamImage2)
                        totalTeam.text= "${items.team.size - 2} lainnya"

                    }
                }


            }
        }
    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<Task>(){
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}
