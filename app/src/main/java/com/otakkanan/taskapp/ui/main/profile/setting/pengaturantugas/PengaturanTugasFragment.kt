package com.otakkanan.taskapp.ui.main.profile.setting.pengaturantugas

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otakkanan.taskapp.R

class PengaturanTugasFragment : Fragment() {

    companion object {
        fun newInstance() = PengaturanTugasFragment()
    }

    private val viewModel: PengaturanTugasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pengaturan_tugas, container, false)
    }
}