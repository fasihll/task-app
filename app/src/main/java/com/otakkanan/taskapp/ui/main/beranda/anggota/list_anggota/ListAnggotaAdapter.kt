package com.otakkanan.taskapp.ui.main.beranda.anggota.list_anggota

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.AnggotaItemBinding
import com.otakkanan.taskapp.databinding.AnggotaListItemBinding
import com.otakkanan.taskapp.databinding.SubTugasListBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.list_anggota.ListAnggotaActivity

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
                            .md_theme_primary))
                        roleContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color
                            .colorPurpleContainer))
                    }
                    "Manager" -> {
                        role.text = items.role
                        role.setTextColor(ContextCompat.getColor(itemView.context, R.color
                            .colorGold))
                        roleContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color
                            .colorGoldContainer))
                    }
                    else -> {
                        roleContainer.visibility = View.GONE
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
