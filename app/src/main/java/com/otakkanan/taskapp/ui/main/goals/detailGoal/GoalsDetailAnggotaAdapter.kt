package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.AnggotaItemBinding
import com.otakkanan.taskapp.databinding.GoalsDetailTeamItemBinding

class GoalsDetailAnggotaAdapter(private val onAddClick: (Team) -> Unit) :  ListAdapter<Team, GoalsDetailAnggotaAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GoalsDetailTeamItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        val itemCount = currentList.size
        holder.bind(items,position, itemCount,onAddClick)
    }

    class MyViewHolder(private val binding: GoalsDetailTeamItemBinding): RecyclerView.ViewHolder(binding
        .root) {

        fun bind(items: Team,position: Int,size: Int,onAddClick: (Team) -> Unit){
            with(binding){
                if (position != 0){
                    itemView.translationX = (-40*position).toFloat()
                }
                if (position == size-1){
                    btnContainer.visibility = View.VISIBLE
                }
                btnTambah.setOnClickListener{
                    onAddClick(items)
                }
               if (items != null){
                   imageAnggota.visibility = View.VISIBLE
                   Glide.with(itemView.context)
                       .load(items.image)
                       .into(imageAnggota)
               }
            }
        }

    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<Team>(){
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem == newItem
            }
        }
    }
}
