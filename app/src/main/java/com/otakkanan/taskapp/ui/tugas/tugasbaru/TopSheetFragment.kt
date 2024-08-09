package com.otakkanan.taskapp.ui.tugas.tugasbaru

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentTopSheetBinding

class TopSheetFragment : Fragment() {
    private var _binding: FragmentTopSheetBinding? = null
    private val binding get() = _binding!!
    private var listener: ButtonClickCompleteListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTopSheetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    fun setListener(listener: ButtonClickCompleteListener) {
        this.listener = listener
    }

    interface ButtonClickCompleteListener {
        fun onButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}