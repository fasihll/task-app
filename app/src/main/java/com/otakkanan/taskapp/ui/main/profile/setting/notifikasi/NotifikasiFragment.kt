package com.otakkanan.taskapp.ui.main.profile.setting.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.otakkanan.taskapp.databinding.FragmentNotifikasiBinding
import com.otakkanan.taskapp.utils.BaseFragment

class NotifikasiFragment : BaseFragment() {

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
        // Listener untuk menu
//        binding.menuPengaturanTugasContainer.setOnClickListener {
//            findNavController().navigate(R.id.action_settingFragment_to_pengaturanTugasFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}