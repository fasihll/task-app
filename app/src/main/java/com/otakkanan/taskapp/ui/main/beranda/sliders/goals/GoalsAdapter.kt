package com.otakkanan.taskapp.ui.main.beranda.sliders.goals

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.GoalsBannerListBinding
import com.otakkanan.taskapp.databinding.TasksBannerListBinding
import com.otakkanan.taskapp.ui.main.beranda.detail_task.DetailTaskActivity

class GoalsAdapter :  ListAdapter<Goal, GoalsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GoalsBannerListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: GoalsBannerListBinding): RecyclerView.ViewHolder(binding
        .root) {


        fun bind(items: Goal){
            with(binding){

                if (position.mod(2) == 0){
                    binding.goalsCard.setCardBackgroundColor(Color.parseColor("#0D2F77"))
                    goalsTitle.setTextColor(Color.parseColor("#FFFFFF"))
                    goalsCount.setTextColor(Color.parseColor("#FFFFFF"))
                    txtProgress.setTextColor(Color.parseColor("#FFFFFF"))
                    teamImage1.borderColor = Color.parseColor("#0D2F77")
                    teamImage2.borderColor = Color.parseColor("#0D2F77")
                    totalTeamContainer.strokeColor = Color.parseColor("#0D2F77")
                }else{
                    binding.goalsCard.setCardBackgroundColor(Color.parseColor("#E4DAFD"))
                    goalsTitle.setTextColor(Color.parseColor("#000000"))
                    goalsCount.setTextColor(Color.parseColor("#000000"))
                    txtProgress.setTextColor(Color.parseColor("#000000"))
                    teamImage1.borderColor = Color.parseColor("#E4DAFD")
                    teamImage2.borderColor = Color.parseColor("#E4DAFD")
                    totalTeamContainer.strokeColor = Color.parseColor("#E4DAFD")
                }
                binding.goalsCard.backgroundTintMode = PorterDuff.Mode.SRC_ATOP

                goalsTitle.text = items.name
                goalsCount.text = items.target!!.size.toString()+" Target" ?: "0 Target"
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
//                    val intent = Intent(itemView.context,GoalsActivity::class.java)
//                    intent.putExtra(DetailTaskActivity.TAG,items)
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(binding.goalsTitle, "title")
//                        )
//                    itemView.context.startActivity(intent,optionsCompat.toBundle())
                    Toast.makeText(itemView.context,"Somming soon..........",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<Goal>(){
            override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem == newItem
            }
        }
    }
}
