package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.databinding.FragmentGoalDetailBinding
//import com.otakkanan.taskapp.ui.adapters.TeamAdapter
//import com.otakkanan.taskapp.ui.adapters.TargetAdapter

class GoalDetailFragment : Fragment() {

    private var _binding: FragmentGoalDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var goal: Goal
//    private lateinit var teamAdapter: TeamAdapter
//    private lateinit var targetAdapter: TargetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data Goal dari arguments
        goal = arguments?.getParcelable("goal") ?: return

        // Set data ke elemen UI
        setupGoalDetails()
        setupRecyclerViews()
    }

    private fun setupGoalDetails() {
        // Set data detail goal
        binding.tvGoalName.text = goal.name ?: "No title"
        binding.tvGoalDescription.text = goal.description ?: "No description"
        binding.tvStartGoalDates.text = "Start Date: ${goal.startDate ?: "N/A"}"
        binding.tvEndGoalDates.text = "End Date: ${goal.endDate ?: "N/A"}"
        binding.tvGoalPriority.text = "Priority: ${goal.priority ?: "N/A"}"

        // Progress bar
        binding.txtProgress.text = "${goal.progress ?: 0}%"
        binding.progressBar.progress = goal.progress ?: 0
    }

    private fun setupRecyclerViews() {
        // Setup RecyclerView untuk Tim
//        teamAdapter = TeamAdapter(goal.team ?: emptyList())
        binding.rvTeam.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvTeam.adapter = teamAdapter

        // Setup RecyclerView untuk Target
//        targetAdapter = TargetAdapter(goal.targets ?: emptyList())
        binding.rvTargets.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvTargets.adapter = targetAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
