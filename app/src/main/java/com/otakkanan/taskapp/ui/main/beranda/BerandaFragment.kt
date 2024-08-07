package com.otakkanan.taskapp.ui.main.beranda

import android.graphics.Color
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import com.otakkanan.taskapp.ui.tugas.tugasbaru.TugasBaruActivity

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val berandaViewModel =
            ViewModelProvider(this).get(BerandaViewModel::class.java)

        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            searchBar.navigationIcon = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                false
            }
        }

        buttonToTugasBaru()

        return root
    }

    private fun buttonToTugasBaru(){
        binding.btnAddTugasBaru.setOnClickListener {
            navigateToTugasBaru()
        }
    }

    private fun navigateToTugasBaru() {
        startActivity(Intent(requireContext(), TugasBaruActivity::class.java))
        requireActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}