package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Riwayat

class RiwayatAdapter(
    private val riwayatList: List<Riwayat>
) : RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    inner class RiwayatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtName: TextView = view.findViewById(R.id.txtName)
        private val txtBadge: TextView = view.findViewById(R.id.txtBadge)

        fun bind(riwayat: Riwayat) {
            txtName.text = riwayat.targetName

            if (riwayat.amount == null){
                txtBadge.text = "Dibuat: "+riwayat.created_at
                txtBadge.setBackgroundResource(R.drawable.badge_riwayat_grey)

            }else{
                if (riwayat.amount!! > 0){
                    txtBadge.text = "+${riwayat.amount}"
                    txtBadge.setBackgroundResource(R.drawable.badge_riwayat_green)
                }else{
                    txtBadge.text = "-${riwayat.amount}"
                    txtBadge.setBackgroundResource(R.drawable.badge_riwayat_red)
                }

            }


//            txtBadge.text = if (riwayat.amount!! > 0) "+${riwayat.amount}" else "${riwayat.amount}"
//
//            // Ubah background badge sesuai nilai
//            val badgeBackground = if (riwayat.amount > 0) {
//                R.drawable.badge_riwayat_green
//            } else {
//                R.drawable.badge_riwayat_red
//            }
//            txtBadge.setBackgroundResource(badgeBackground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return RiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        holder.bind(riwayatList[position])
    }

    override fun getItemCount(): Int = riwayatList.size
}