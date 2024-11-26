package com.otakkanan.taskapp.ui.main.profile.setting.notifikasi

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentNotifikasiBinding

class NotifikasiFragment : Fragment() {

    companion object {
        fun newInstance() = NotifikasiFragment()
    }

    private val viewModel: NotifikasiViewModel by viewModels()

    // Deklarasi objek binding
    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)

        hideBottomNavigation()
        setupToolbar()

//        binding..setOnClickListener {
//            findNavController().navigate(R.id.action_settingFragment_to_pengaturanTugasFragment)
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        showBottomNavigation()
    }

    private fun hideBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}