package com.otakkanan.taskapp.ui.main.beranda.anggota

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.ui.main.beranda.anggota.undangan.AddAnggotaFragment
import com.otakkanan.taskapp.ui.main.beranda.anggota.partisipan.ListAnggotaFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val task: Task?
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
                ListAnggotaFragment.newInstance(task)
            else
                AddAnggotaFragment.newInstance(task)
    }
}