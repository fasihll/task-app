package com.otakkanan.taskapp.ui.main.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.databinding.FragmentGoalsBinding
import com.otakkanan.taskapp.utils.Helper.loadJSONFromAssets
import com.otakkanan.taskapp.utils.Helper.parseGoalsJson

class GoalsFragement : Fragment() {

    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!

    // Data dummy
    private val items = listOf(
        Goal(name = "Goal 1", description = "Complete the project documentation", endDate = "2024-11-01", progress = 40),
        Goal(name = "Goal 2", description = "Fix bugs in the application", endDate = "2024-11-15", progress = 60),
        Goal(name = "Goal 3", description = "Prepare the project presentation", endDate = "2024-11-20", progress = 10),
        Goal(name = "Goal 4", description = "Deploy to production environment", endDate = "2024-12-01", progress = 90)
    )

    private lateinit var adapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Membaca file JSON dari assets
        val jsonString = loadJSONFromAssets(requireContext(), "goals_dummy.json")

        // Mengonversi JSON ke List of Goals
        val goalsList: List<Goal> = parseGoalsJson(jsonString)

        // Inisialisasi adapter
        adapter = GoalAdapter(goalsList)

        // Setup RecyclerView
        binding.rvGoals.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@GoalsFragement.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}