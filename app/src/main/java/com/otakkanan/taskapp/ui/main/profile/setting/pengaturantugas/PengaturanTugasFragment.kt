package com.otakkanan.taskapp.ui.main.profile.setting.pengaturantugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentPengaturanTugasBinding
import com.otakkanan.taskapp.utils.BaseFragment

class PengaturanTugasFragment : BaseFragment() {

    // Deklarasi objek binding
    private var _binding: FragmentPengaturanTugasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentPengaturanTugasBinding.inflate(inflater, container, false)

        setupToolbar()
        setupListeners()

        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        // Tambahkan listener untuk pengaturan tugas jika diperlukan
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

