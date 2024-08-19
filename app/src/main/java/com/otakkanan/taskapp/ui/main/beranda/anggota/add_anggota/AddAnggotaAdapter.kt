package com.otakkanan.taskapp.ui.main.beranda.anggota.add_anggota

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.AddAnggotaPageListBinding

class AddAnggotaAdapter :  ListAdapter<Team, AddAnggotaAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AddAnggotaPageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        val itemCount = currentList.size
        holder.bind(items,position, itemCount)
    }

    class MyViewHolder(private val binding: AddAnggotaPageListBinding): RecyclerView.ViewHolder(binding
        .root) {

        fun bind(items: Team,position: Int,size: Int){
            with(binding){

              try {
                  Glide.with(itemView.context)
                      .load(items.image)
                      .into(image)
                  checkbox.text = items.name
                  checkbox.isChecked = true
              }catch (e: Exception){
                  Toast.makeText(itemView.context,"Koosng",Toast.LENGTH_SHORT).show()
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
