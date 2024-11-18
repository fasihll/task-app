package com.otakkanan.taskapp.ui.main.profile.setting.notifikasi

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otakkanan.taskapp.R

class NotifikasiFragment : Fragment() {

    companion object {
        fun newInstance() = NotifikasiFragment()
    }

    private val viewModel: NotifikasiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_notifikasi, container, false)
    }
}