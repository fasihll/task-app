package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.data.model.Riwayat
import com.otakkanan.taskapp.data.model.Target
import com.otakkanan.taskapp.databinding.FragmentGoalDetailBinding


class GoalDetailFragment : Fragment() {

    private var _binding: FragmentGoalDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var goal: Goal
    private lateinit var targetAdapter: TargetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNavigation()
        setupToolbar()

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
        binding.tvEndGoalDates.text = "Berakhir : ${goal.endDate ?: "N/A"}"


        // Progress bar
        binding.txtProgress.text = "${goal.progress ?: 0}%"
        binding.progressBar.progress = goal.progress ?: 0
    }

    private fun setupRecyclerViews() {
        // Setup untuk RecyclerView Target
        val targetList: List<Target> = goal.target ?: emptyList()
        targetAdapter = TargetAdapter(targetList) { target ->
            Snackbar.make(
                binding.root,
                "Anda mengklik target: ${target.name ?: "Unnamed Target"}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        binding.rvTarget.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTarget.adapter = targetAdapter

        // Setup untuk RecyclerView Riwayat
        val riwayatList = getDummyRiwayat()
        val riwayatAdapter = RiwayatAdapter(riwayatList)
        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayat.adapter = riwayatAdapter
    }

    private fun getDummyRiwayat(): List<Riwayat> {
        return listOf(
            Riwayat("Penambahan Target", +200),
            Riwayat("Pengurangan Target", -100),
            Riwayat("Bonus", +50),
            Riwayat("Kesalahan Input", -30)
        )
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun hideBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        showBottomNavigation()
    }
}
