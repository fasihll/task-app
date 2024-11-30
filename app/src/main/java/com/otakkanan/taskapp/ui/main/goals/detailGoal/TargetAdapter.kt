package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.Target
import com.otakkanan.taskapp.databinding.ItemTargetBinding

class TargetAdapter(
    private val itemList: List<Target>,
    private val onItemClick: (Target) -> Unit
) : RecyclerView.Adapter<TargetAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTargetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(target: Target) {
            // Mengisi tampilan berdasarkan data
            binding.txtTitle.text = target.name ?: "Unnamed Target"
            binding.txtStartEndValue.text = "${target.startValue ?: 0} / ${target.endValue ?: 0}"
            binding.linearProgress.progress = target.progress ?: 0

            // Menambahkan listener klik pada item
            binding.root.setOnClickListener {
                onItemClick(target) // Memanggil callback saat item di-klik
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTargetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}
