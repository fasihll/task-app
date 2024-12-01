package com.otakkanan.taskapp.ui.main.beranda.anggota.partisipan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.AnggotaListItemBinding

class ListAnggotaAdapter :  ListAdapter<Team, ListAnggotaAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AnggotaListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        val itemCount = currentList.size
        holder.bind(items,position, itemCount)
    }

    class MyViewHolder(private val binding: AnggotaListItemBinding): RecyclerView.ViewHolder(binding
        .root) {

        fun bind(items: Team,position: Int,size: Int){
            with(binding){



                Glide.with(itemView.context)
                    .load(items.image)
                    .into(image)
                title.text = items.name
                when(items.role){
                    "Admin" -> {
                        role.text = items.role
                        role.setTextColor(ContextCompat.getColor(itemView.context, R.color
                            .colorOnPurpleContainer))
                        roleContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color
                            .colorPurpleContainer))
                    }
                    "Manager" -> {
                        role.text = items.role
                        role.setTextColor(ContextCompat.getColor(itemView.context, R.color
                            .colorOnGoldContainer))
                        roleContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color
                            .colorGoldContainer))
                    }
                    else -> {
                        role.text = items.role
                        role.setTextColor(ContextCompat.getColor(itemView.context, R.color
                            .md_theme_onSecondaryContainer))
                        roleContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color
                            .md_theme_secondaryContainer))
                    }
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
