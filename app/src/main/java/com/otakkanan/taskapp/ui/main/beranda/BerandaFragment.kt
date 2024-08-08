package com.otakkanan.taskapp.ui.main.beranda

import android.graphics.Color
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import com.otakkanan.taskapp.ui.tugas.tugasbaru.TugasBaruActivity

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private  val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
    ).apply {
        setMargins(0,0,0,0)
    }

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

            val tasksList = arrayListOf(
                Task(
                    title = "Desain UI",
                    progress = 50.0,
                    team = listOf(
                        Team(name = "fasih", image = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                        Team(name = "marzuki", image = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                        Team(name = "ahnaf", image = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                    )
                )
            )

           /* val taskAdapter = TaskAdapter()
            vpTaskBanner.adapter = taskAdapter
            taskAdapter.submitList(tasksList)

            val taskIndicator = Array(tasksList.size){ ImageView(requireContext()) }

            taskIndicator.forEach {
                it.setImageResource(

                )
            }*/
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