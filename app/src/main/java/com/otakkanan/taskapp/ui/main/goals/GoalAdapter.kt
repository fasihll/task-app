package com.otakkanan.taskapp.ui.main.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.databinding.ItemGoalBinding

class GoalAdapter(
    private val itemList: List<Goal>,
    private val onItemClick: (Goal) -> Unit // Callback untuk menangani klik pada item
) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(goal: Goal) {
            // Mengisi tampilan secara manual
            binding.txtTitle.text = goal.name
            binding.txtDescription.text = goal.description
            binding.txtDate.text = goal.endDate
            binding.txtProgress.text = "${goal.progress ?: 0}%"
            binding.progressBar.progress = goal.progress ?: 0

            // Menambahkan listener klik pada item
            binding.root.setOnClickListener {
                onItemClick(goal) // Memanggil callback saat item di-klik
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}
