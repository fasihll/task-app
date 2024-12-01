package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.data.model.Riwayat
import com.otakkanan.taskapp.data.model.Target
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.FragmentGoalDetailBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity
import com.otakkanan.taskapp.ui.main.beranda.detail_task.AnggotaAdapter
import com.otakkanan.taskapp.utils.BaseFragment


class GoalDetailFragment : BaseFragment() {

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
        setupToolbar()

        // Ambil data Goal dari arguments
        goal = arguments?.getParcelable("goal") ?: return

        // Set data ke elemen UI
        setupAnggotaRecyclerview(goal)
        setupGoalDetails()
        setupRecyclerViews(goal)
    }

    private fun setupAnggotaRecyclerview(goal: Goal?) {
        with(binding){
            val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager
                .HORIZONTAL,false)
            rvAnggota.layoutManager = layoutManager


            val adapter = GoalsDetailAnggotaAdapter { team ->
                // Handle button click here
                val intent = Intent(requireContext(), AnggotaActivity::class.java)
                intent.putParcelableArrayListExtra(AnggotaActivity.TAG, ArrayList(goal!!.team ?: emptyList()))
                startActivity(intent)
            }
            rvAnggota.adapter = adapter
            val limitedTeam = goal!!.team?.take(1)
            adapter.submitList(limitedTeam)
        }
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

    private fun setupRecyclerViews(goal: Goal?) {
        // Setup untuk RecyclerView Target
//        val targetList: List<Target> = goal.target ?: emptyList()
        targetAdapter = TargetAdapter(goal?.target!!) { target ->
            Snackbar.make(
                binding.root,
                "Anda mengklik target: ${target.name ?: "Unnamed Target"}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        binding.rvTarget.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTarget.adapter = targetAdapter

        // Setup untuk RecyclerView Riwayat
//        val riwayatList = getDummyRiwayat()
        val riwayatAdapter = RiwayatAdapter(goal.riwayat!!)
        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayat.adapter = riwayatAdapter

        // Tambahkan Divider ke RecyclerView
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvRiwayat.addItemDecoration(dividerItemDecoration)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
