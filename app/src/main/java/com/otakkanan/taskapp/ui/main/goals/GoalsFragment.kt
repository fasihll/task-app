package com.otakkanan.taskapp.ui.main.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.databinding.FragmentGoalsBinding
import com.otakkanan.taskapp.utils.Helper.loadJSONFromAssets
import com.otakkanan.taskapp.utils.Helper.parseGoalsJson

class GoalsFragment : Fragment() {

    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!

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

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Membaca file JSON dari assets
        val jsonString = loadJSONFromAssets(requireContext(), "goals_dummy.json")

        // Mengonversi JSON ke List of Goals
        val goalsList: List<Goal> = parseGoalsJson(jsonString)

        // Inisialisasi adapter
        adapter = GoalAdapter(goalsList) { goal ->
            val bundle = Bundle().apply {
                putParcelable("goal", goal)
            }
            findNavController().navigate(R.id.action_goalsFragment_to_goalDetailFragment, bundle)
        }

        // Setup RecyclerView
        binding.rvGoals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoals.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
