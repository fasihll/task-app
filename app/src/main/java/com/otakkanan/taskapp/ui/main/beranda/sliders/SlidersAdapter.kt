package com.otakkanan.taskapp.ui.main.beranda.sliders

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.otakkanan.taskapp.ui.main.beranda.sliders.goals.GoalsFragment
import com.otakkanan.taskapp.ui.main.beranda.sliders.tim.TimFragment

class SlidersAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TimFragment()
            1 -> GoalsFragment()
            else -> TimFragment()
        }
    }
}