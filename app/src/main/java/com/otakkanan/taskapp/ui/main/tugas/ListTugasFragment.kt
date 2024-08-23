package com.otakkanan.taskapp.ui.main.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import com.otakkanan.taskapp.R

class ListTugasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_tugas, container, false)

        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        val bookmarkIcon: View = view.findViewById(R.id.toolbar_bookmark)

        val fragmentList = listOf(
            TugasFragment(),
            TugasBerulangFragment(),
            KebiasaanFragment()
        )
        val adapter = FragmentPagerAdapter(this, fragmentList)
        viewPager.adapter = adapter

        // Attach TabLayoutMediator to synchronize TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Tugas"
                1 -> "Tugas Berulang"
                2 -> "Kebiasaan"
                else -> null
            }
        }.attach()

        bookmarkIcon.setOnClickListener {
            findNavController().navigate(R.id.action_listTugasFragment_to_listTersimpanFragment)
        }

        return view
    }
}
