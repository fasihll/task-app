package com.otakkanan.taskapp.ui.main.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Evaluasi
import com.otakkanan.taskapp.data.model.Manajer
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import com.otakkanan.taskapp.ui.main.beranda.sliders.SlidersAdapter
import com.otakkanan.taskapp.ui.main.beranda.sliders.tim.TaskAdapter
import com.otakkanan.taskapp.ui.main.beranda.tugas_harian.TaskDayAdapter
import com.otakkanan.taskapp.ui.tugas.tugasbaru.TugasBaruActivity
import com.otakkanan.taskapp.ui.tugas.tugasberulangbaru.TugasBerulangBaruActivity
import java.time.LocalTime

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2

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


        setupViewPager2() //tim
        setupTaskDayRecyclerview()
        buttonToTugasBaru()

        return root
    }

    private fun setupViewPager2() {
        with(binding){

            vpSliders.adapter = SlidersAdapter(requireActivity() as AppCompatActivity)
            vpSliders.isUserInputEnabled = false

            btnTim.setOnClickListener{
                vpSliders.currentItem = 0
                setActiveButton(btnTim,txtTim, btnTarget,txtTarget)
            }

            btnTarget.setOnClickListener{
                vpSliders.currentItem = 1
                setActiveButton(btnTarget,txtTarget,btnTim,txtTim)
            }
        }

    }

    private fun setActiveButton(activeButton: MaterialCardView,activeText: TextView, inactiveButton:
    MaterialCardView,inanctiveText: TextView) {
        activeButton.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color
            .md_theme_primaryFixed))
        activeButton.strokeWidth = 2
        activeButton.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.md_theme_primaryContainer))
        activeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_primaryContainer))

        inactiveButton.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color
            .md_theme_secondaryContainer))
        inactiveButton.strokeWidth = 2
        inactiveButton.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.md_theme_onSecondaryFixedVariant))
        inanctiveText.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_onSecondaryFixedVariant))
    }


    private fun setupTaskDayRecyclerview() {
        val taskDay = arrayListOf(
            TaskDay(type = 1, title = "Tugas 1 — Mengerjakan beranda",time= LocalTime.of(10,0,0),
                isDone =
            true, isRepeate = false),
            TaskDay(type = 2, evaluasi = arrayListOf(Evaluasi(type = 2, maxCount = 2)), title ="Kebiasaan 1 — " +
                    "Literasi",
                time=
            LocalTime.of
                (11,0,0),
                isDone = false, isRepeate = false),
            TaskDay(type = 1, title ="Tugas 2 — Mengerjakan detail",time= LocalTime.of(11,0,0),isDone =
            true, isRepeate = true),
            TaskDay(type = 1, title ="Tugas 3 — Mengerjakan member level page",time= LocalTime.of(13,0,0),
                isDone = false, isRepeate = false),
            TaskDay(type = 1, title ="Tugas 4 — Mengerjakan tambah anggota page",time= LocalTime.of(17,0,0)
                ,isDone = false, isRepeate = false)
        )

        with(binding){
            val layoutManager = LinearLayoutManager(requireContext())
            rvTaskDay.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireContext(),layoutManager.orientation)
            rvTaskDay.addItemDecoration(itemDecoration)

            val adapter = TaskDayAdapter()
            rvTaskDay.adapter = adapter
            adapter.submitList(taskDay)
        }
    }

    private fun buttonToTugasBaru(){
        binding.btnAddTugasBaru.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_button, null)

            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .show()

            val menu1 = dialogView.findViewById<LinearLayout>(R.id.menu_1)
            val menu2 = dialogView.findViewById<LinearLayout>(R.id.menu_2)
            val menu3 = dialogView.findViewById<LinearLayout>(R.id.menu_3)
            val menu4 = dialogView.findViewById<LinearLayout>(R.id.menu_4)
            val menu5 = dialogView.findViewById<LinearLayout>(R.id.menu_5)
            menu1.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(requireContext(),TugasBaruActivity::class.java))
            }
            menu2.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(requireContext(), TugasBerulangBaruActivity::class.java))
            }
            menu3.setOnClickListener {
//                dialog.dismiss()
//                startActivity(Intent(requireContext(),KebiasaanActivity::class.java))
            }
            menu4.setOnClickListener {
//                dialog.dismiss()
//                startActivity(Intent(requireContext(),AddTargetActivity::class.java))
            }
            menu4.setOnClickListener {
//                dialog.dismiss()
//                startActivity(Intent(requireContext(),ProyekTimActivity::class.java))
            }
        }
    }

    private fun navigateToTugasBaru() {
        startActivity(Intent(requireContext(), TugasBaruActivity::class.java))
        requireActivity()
    }

    override fun onPause() {
        super.onPause()
        with(binding){
            vpSliders.currentItem = 0
            setActiveButton(btnTim,txtTim, btnTarget,txtTarget)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}