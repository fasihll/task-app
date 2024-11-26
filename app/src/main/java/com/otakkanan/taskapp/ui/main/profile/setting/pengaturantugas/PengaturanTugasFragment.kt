package com.otakkanan.taskapp.ui.main.profile.setting.pengaturantugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentPengaturanTugasBinding

class PengaturanTugasFragment : Fragment() {

    companion object {
        fun newInstance() = PengaturanTugasFragment()
    }

    private val viewModel: PengaturanTugasViewModel by viewModels()

    // Deklarasi objek binding
    private var _binding: FragmentPengaturanTugasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentPengaturanTugasBinding.inflate(inflater, container, false)

        hideBottomNavigation()
        setupToolbar()

//      Handle klik untuk dropdown
        binding.menuOpsiPengurutanContainer.setOnClickListener {
            val opsiPengurutan = binding.opsiPengurutan
            val icoonDropdown = binding.icDropdown
            if (opsiPengurutan.visibility == View.GONE) {
                opsiPengurutan.visibility = View.VISIBLE
                icoonDropdown.setImageResource(R.drawable.ic_up_dropdown)
            } else {
                opsiPengurutan.visibility = View.GONE
                icoonDropdown.setImageResource(R.drawable.ic_down_dropdown)
            }
        }

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
