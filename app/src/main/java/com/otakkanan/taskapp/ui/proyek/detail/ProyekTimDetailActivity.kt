package com.otakkanan.taskapp.ui.proyek.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.ActivityDetailTaskBinding
import com.otakkanan.taskapp.databinding.ActivityProyekTimDetailBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity
import com.otakkanan.taskapp.ui.main.beranda.detail_task.AnggotaAdapter
import com.otakkanan.taskapp.ui.main.beranda.detail_task.SubTugasAdapter
import com.otakkanan.taskapp.ui.tugas.tugasbaru.TugasBaruActivity
import com.otakkanan.taskapp.utils.Helper
import java.time.LocalTime

class ProyekTimDetailActivity : AppCompatActivity() {

    private var _binding: ActivityProyekTimDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityProyekTimDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopBar()

//        val task: Task? = intent.getParcelableExtra(TAG)

        val jsonString = Helper.loadJSONFromAssets(this@ProyekTimDetailActivity, "task_dummy.json")

        // Mengonversi JSON ke List of Goals
        val taskList: List<Task> = Helper.parseTaskJson(jsonString)
        val task: Task? = taskList[0]

        with(binding){
            greeting.text = task?.title!!.replace("\n","")
            btnAddTugas.setOnClickListener{
                setupDialogAddTugas(task)
            }
        }
        setupAnggotaRecyclerview(task)
        setupSubTugasRecyclerview()
        setupView(task)

    }

    private fun setupDialogAddTugas(task: Task?) {
        val dialogView = layoutInflater.inflate(R.layout.proyek_tim_add_tugas_dialog, null)

        val dialog = MaterialAlertDialogBuilder(this@ProyekTimDetailActivity)
            .setView(dialogView)
            .show()

        val edNamaTugas = dialogView.findViewById<EditText>(R.id.ed_nama_tugas)
        val rbInterval = dialogView.findViewById<RadioButton>(R.id.rb_interval_pengukuran)
        val rbBerlangsung = dialogView.findViewById<RadioButton>(R.id.rb_sedang_berlansung_selesai)
        val rbMataUang = dialogView.findViewById<RadioButton>(R.id.rb_mata_uang)
        val rvAnggota = dialogView.findViewById<RecyclerView>(R.id.rv_anggota)
        val btnSelsai = dialogView.findViewById<TextView>(R.id.btn_dialog_selesai)


        val subInterval = dialogView.findViewById<LinearLayout>(R.id.sub_interval_pengukuran)
        val edIntervalMulai = dialogView.findViewById<EditText>(R.id.ed_interval_mulai)
        val edIntervalTarget = dialogView.findViewById<EditText>(R.id.ed_interval_target)


        val subBerlansung = dialogView.findViewById<LinearLayout>(R.id.sub_berlangsung)
        val swBerlangsung = dialogView.findViewById<TextView>(R.id.sw_berlangsung)
        val swSelesai = dialogView.findViewById<TextView>(R.id.sw_selesai)


        val subMataUang = dialogView.findViewById<LinearLayout>(R.id.sub_mata_uang)
        val edMataUangMulai = dialogView.findViewById<EditText>(R.id.ed_matauang_mulai)
        val edMataUangTarget = dialogView.findViewById<EditText>(R.id.ed_matauang_target)

        btnSelsai.setOnClickListener{
            dialog.dismiss()
        }

        val layoutManager = LinearLayoutManager(this@ProyekTimDetailActivity,LinearLayoutManager
            .HORIZONTAL,false)
        rvAnggota.layoutManager = layoutManager


        val adapter = AnggotaAdapter { team ->
            // Handle button click here
            val intent = Intent(this@ProyekTimDetailActivity, AnggotaActivity::class.java)
            intent.putParcelableArrayListExtra(AnggotaActivity.TAG, ArrayList(task!!.team ?:
            emptyList()))
            startActivity(intent)
//                val intent = Intent(this@DetailTaskActivity, AnggotaActivity::class.java)
//                startActivity(intent)
        }
        rvAnggota.adapter = adapter
        val limitedTeam = task!!.team?.take(2)
        adapter.submitList(limitedTeam)


        rbInterval.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = subInterval,
                    otherSubLayouts = listOf(subBerlansung, subMataUang)
                )
            }
        }

        rbBerlangsung.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = subBerlansung,
                    otherSubLayouts = listOf(subInterval, subMataUang)
                )
            }
        }

        rbMataUang.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                updateSubLayoutVisibility(
                    selectedSubLayout = subMataUang,
                    otherSubLayouts = listOf(subInterval, subBerlansung)
                )
            }
        }


        swBerlangsung.setOnClickListener {
            updateSwitchStyles(swBerlangsung, swSelesai)
        }


        swSelesai.setOnClickListener {
            updateSwitchStyles(swSelesai, swBerlangsung)
        }

    }

    fun updateSwitchStyles(selectedSwitch: TextView, unselectedSwitch: TextView) {
        selectedSwitch.setBackgroundResource(R.drawable.selected_berlangsung_background)
        selectedSwitch.setTextColor(ContextCompat.getColor(selectedSwitch.context, R.color.md_theme_onSecondary))

        unselectedSwitch.setBackgroundResource(R.color.md_theme_primaryFixed)
        unselectedSwitch.setTextColor(ContextCompat.getColor(unselectedSwitch.context, R.color
            .md_theme_onSecondaryFixed))
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

    private fun setupAnggotaRecyclerview(task: Task?) {

        with(binding){
            val layoutManager = LinearLayoutManager(this@ProyekTimDetailActivity, LinearLayoutManager
                .HORIZONTAL,false)
            rvAnggota.layoutManager = layoutManager


            val adapter = AnggotaAdapter { team ->
                // Handle button click here
                val intent = Intent(this@ProyekTimDetailActivity, AnggotaActivity::class.java)
                intent.putParcelableArrayListExtra(AnggotaActivity.TAG, ArrayList(task!!.team ?:
                emptyList()))
                startActivity(intent)
//                val intent = Intent(this@ProyekTimDetailActivity, AnggotaActivity::class.java)
//                startActivity(intent)
            }
            rvAnggota.adapter = adapter
            adapter.submitList(task!!.team)
        }
    }

    private fun setupView(task: Task?) {
        with(binding){
            Glide.with(this@ProyekTimDetailActivity)
                .load(task?.manajer?.get(0)?.image)
                .into(imageManajer)
            manajerName.text = task?.manajer!![0].name
            deskripsi.text = task.deskripsi
            progresBar.progress = task.progress!!
            progressBarinsideText.text = "${task.progress}%"
        }
    }

    private fun setupSubTugasRecyclerview() {
        val taskDay = arrayListOf(
            TaskDay(title = "Tugas 1 — Mengerjakan beranda",time= LocalTime.of(10,0,0), isDone =
            true),
            TaskDay(title ="Tugas 2 — Mengerjakan detail",time= LocalTime.of(11,0,0),isDone = true),
            TaskDay(title ="Tugas 3 — Mengerjakan member level page",time= LocalTime.of(13,0,0),
                isDone = false),
            TaskDay(title ="Tugas 4 — Mengerjakan tambah anggota page",time= LocalTime.of(17,0,0)
                ,isDone = false)
        )

        with(binding){
            val layoutManager = LinearLayoutManager(this@ProyekTimDetailActivity)
            rvSubtugas.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@ProyekTimDetailActivity,layoutManager.orientation)
            rvSubtugas.addItemDecoration(itemDecoration)

            val adapter = SubTugasAdapter()
            rvSubtugas.adapter = adapter
            adapter.submitList(taskDay)
        }
    }

    private fun setupTopBar() {
        with(binding){
            btnBack.setOnClickListener{ finish() }
//         btnOption.setOnClickListener{ }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        val TAG = "DETAIL_TASK"
    }
}