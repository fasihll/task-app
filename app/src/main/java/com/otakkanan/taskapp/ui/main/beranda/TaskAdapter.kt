package com.otakkanan.taskapp.ui.main.beranda

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.TasksBannerListBinding
import com.otakkanan.taskapp.ui.main.beranda.detail_task.DetailTaskActivity

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

                if (position.mod(2) == 0){
                    binding.tasksCard.setCardBackgroundColor(Color.parseColor("#61346B"))
                    teamImage1.borderColor = Color.parseColor("#61346B")
                    teamImage2.borderColor = Color.parseColor("#61346B")
                    totalTeamContainer.strokeColor = Color.parseColor("#61346B")
                }else{
                    binding.tasksCard.setCardBackgroundColor(Color.parseColor("#F2C75B"))
                    teamImage1.borderColor = Color.parseColor("#F2C75B")
                    teamImage2.borderColor = Color.parseColor("#F2C75B")
                    totalTeamContainer.strokeColor = Color.parseColor("#F2C75B")
                }
                binding.tasksCard.backgroundTintMode = PorterDuff.Mode.SRC_ATOP

                taskTitle.text = items.title
                progresBar.progress = items.progress!!
                when(items.team?.size){
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
                        totalTeamContainer.visibility  = View.VISIBLE
                        Glide.with(itemView.context)
                            .load(items.team!![0].image)
                            .into(teamImage1)
                        Glide.with(itemView.context)
                            .load(items.team[1].image)
                            .into(teamImage2)
                        totalTeam.text= itemView.context.getString(R.string.lainnya, items.team.size - 2)

                    }
                }



                itemView.setOnClickListener {
                    val intent = Intent(itemView.context,DetailTaskActivity::class.java)
                    intent.putExtra(DetailTaskActivity.TAG,items)
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(binding.taskTitle, "title")
                        )
                    itemView.context.startActivity(intent,optionsCompat.toBundle())
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
