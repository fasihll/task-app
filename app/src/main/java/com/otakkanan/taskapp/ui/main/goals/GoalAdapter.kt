package com.otakkanan.taskapp.ui.main.goals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Goal

class GoalAdapter(
    private val itemList: List<Goal>
) : RecyclerView.Adapter<GoalAdapter.ItemViewHolder>() {

    // ViewHolder class
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtProgress: TextView = view.findViewById(R.id.txtProgress)
        val progressBar: CircularProgressIndicator = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        // Set text fields
        holder.txtTitle.text = item.name
        holder.txtDescription.text = item.description
        holder.txtDate.text = item.endDate

        // Update progress bar and percentage text
        holder.progressBar.progress = item.progress!!
        holder.txtProgress.text = "${item.progress}%"
    }

    override fun getItemCount(): Int = itemList.size
}

