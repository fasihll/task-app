package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.data.model.Target
import com.otakkanan.taskapp.databinding.DialogDetailTargetBinding
import com.otakkanan.taskapp.databinding.ItemTargetBinding
import java.text.NumberFormat
import java.util.Locale

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
                openDialog(target)
            }
        }

        private fun openDialog(target: Target) {
            val dialogBinding =
                DialogDetailTargetBinding.inflate(LayoutInflater.from(binding.root.context))
            val dialog = MaterialAlertDialogBuilder(binding.root.context)
                .setView(dialogBinding.root)
                .show()

            // Menampilkan data target di dialog
            dialogBinding.titleMenabung.text = target.name ?: "Unnamed Target"
            dialogBinding.txtStartvalue.text =
                "Rp ${NumberFormat.getNumberInstance(Locale.US).format(target.startValue ?: 0)}"
            dialogBinding.txtEndvalue.text =
                "Rp ${NumberFormat.getNumberInstance(Locale.US).format(target.endValue ?: 0)}"
            dialogBinding.txtSaatIni.text =
                "Rp ${NumberFormat.getNumberInstance(Locale.US).format(target.progress ?: 0)}"

            dialogBinding.btnSimpan.setOnClickListener {
                dialog.dismiss()
            }

            val nilaiAkhir = target.endValue ?: 500000
            val nilaiSaatIni = target.progress ?: 200000

            dialogBinding.slider.addOnChangeListener { _, value, _ ->
                val menabungValue = (nilaiAkhir * value.toInt() / 100)
                dialogBinding.edMenabung.setText(
                    NumberFormat.getNumberInstance(Locale.US).format(menabungValue)
                )

                val totalValue = nilaiSaatIni + menabungValue
                dialogBinding.txtTotal.text =
                    "Rp " + NumberFormat.getNumberInstance(Locale.US).format(totalValue)
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
