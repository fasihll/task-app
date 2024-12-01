package com.otakkanan.taskapp.ui.main.goals.detailGoal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.data.model.Target
import com.otakkanan.taskapp.databinding.DetailGoalsAddTargetDialogBinding
import com.otakkanan.taskapp.databinding.FragmentGoalDetailBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity
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

        // Set Click Listener
        setupOnClickListener()
    }

    private fun setupOnClickListener() {
        binding.btnPlusCard.setOnClickListener {
            setupDialogAddTarget(goal.target?.get(0))
        }
    }

    private fun setupDialogAddTarget(target: Target?) {
        val dialogBinding = DetailGoalsAddTargetDialogBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .show()

        // Akses elemen menggunakan dialogBinding
        dialogBinding.btnDialogSelesai.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.rbIntervalPengukuran.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = dialogBinding.subIntervalPengukuran,
                    otherSubLayouts = listOf(
                        dialogBinding.subBerlangsung,
                        dialogBinding.subMataUang
                    )
                )
            }
        }

        dialogBinding.rbSedangBerlansungSelesai.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = dialogBinding.subBerlangsung,
                    otherSubLayouts = listOf(
                        dialogBinding.subIntervalPengukuran,
                        dialogBinding.subMataUang
                    )
                )
            }
        }

        dialogBinding.rbMataUang.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = dialogBinding.subMataUang,
                    otherSubLayouts = listOf(
                        dialogBinding.subIntervalPengukuran,
                        dialogBinding.subBerlangsung
                    )
                )
            }
        }

        dialogBinding.swBerlangsung.setOnClickListener {
            updateSwitchStyles(dialogBinding.swBerlangsung, dialogBinding.swSelesai)
        }

        dialogBinding.swSelesai.setOnClickListener {
            updateSwitchStyles(dialogBinding.swSelesai, dialogBinding.swBerlangsung)
        }
    }

    fun updateSwitchStyles(selectedSwitch: TextView, unselectedSwitch: TextView) {
        selectedSwitch.setBackgroundResource(R.drawable.selected_berlangsung_background)
        selectedSwitch.setTextColor(
            ContextCompat.getColor(
                selectedSwitch.context,
                R.color.md_theme_onSecondary
            )
        )

        unselectedSwitch.setBackgroundResource(R.color.md_theme_primaryFixed)
        unselectedSwitch.setTextColor(
            ContextCompat.getColor(
                unselectedSwitch.context, R.color
                    .md_theme_onSecondaryFixed
            )
        )
    }

    fun updateSubLayoutVisibility(
        selectedSubLayout: LinearLayout,
        otherSubLayouts: List<LinearLayout>
    ) {

        selectedSubLayout.visibility = View.VISIBLE

        otherSubLayouts.forEach { layout ->
            layout.visibility = View.GONE
            clearEditTexts(layout)
        }
    }


    fun clearEditTexts(layout: LinearLayout) {
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is EditText) {
                child.text.clear()
            }
        }
    }

    private fun setupAnggotaRecyclerview(goal: Goal?) {
        with(binding) {
            val layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager
                    .HORIZONTAL, false
            )
            rvAnggota.layoutManager = layoutManager


            val adapter = GoalsDetailAnggotaAdapter { team ->
                // Handle button click here
                val intent = Intent(requireContext(), AnggotaActivity::class.java)
                intent.putExtra(AnggotaActivity.TAG, goal)
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
        val riwayatAdapter = RiwayatAdapter(goal.riwayat!!)
        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayat.adapter = riwayatAdapter

        // Tambahkan Divider ke RecyclerView
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvRiwayat.addItemDecoration(dividerItemDecoration)
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
