package com.otakkanan.taskapp.ui.main.kalender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.DataModel
import com.otakkanan.taskapp.data.model.SurveyModel
import com.otakkanan.taskapp.data.model.TreatmentModel
import com.otakkanan.taskapp.databinding.CardSurveyBinding
import com.otakkanan.taskapp.databinding.CardTreatmentBinding


class DataAdapter(
    diff: DataDiff,
) : ListAdapter<DataModel, RecyclerView.ViewHolder>(diff) {

    companion object {
        const val TREATMENT = 0x4545
        const val SURVEY = 0x4646
    }

    class TreatmentViewHolder(private val binding: CardTreatmentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TreatmentModel) {
            binding.apply {
                nameT.text = model.name
            }
        }
    }

    class SurveyViewHolder(private val binding: CardSurveyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SurveyModel) {
            binding.apply {
                nameT.text = model.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TREATMENT) TreatmentViewHolder(CardTreatmentBinding.inflate(inflater, parent, false))
        else SurveyViewHolder(CardSurveyBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TreatmentViewHolder -> holder.bind(getItem(position) as TreatmentModel)
            is SurveyViewHolder -> holder.bind(getItem(position) as SurveyModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is TreatmentModel) TREATMENT else SURVEY
    }

    class DataDiff : DiffUtil.ItemCallback<DataModel>() {
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }
}