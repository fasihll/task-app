package com.otakkanan.taskapp.ui.main.beranda.detail_task

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.ActivityDetailTaskBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity
import java.time.LocalTime

class DetailTaskActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopBar()

        val task: Task? = intent.getParcelableExtra(TAG)
        with(binding){
            greeting.text = task?.title!!.replace("\n","")
        }
        setupAnggotaRecyclerview(task)
        setupSubTugasRecyclerview()
        setupView(task)
    }

    private fun setupAnggotaRecyclerview(task: Task?) {
        with(binding){
            val layoutManager = LinearLayoutManager(this@DetailTaskActivity,LinearLayoutManager
                .HORIZONTAL,false)
            rvAnggota.layoutManager = layoutManager


            val adapter = AnggotaAdapter { team ->
                // Handle button click here
                val intent = Intent(this@DetailTaskActivity, AnggotaActivity::class.java)
                intent.putParcelableArrayListExtra(AnggotaActivity.TAG, ArrayList(task!!.team ?:
                emptyList()))
                startActivity(intent)
//                val intent = Intent(this@DetailTaskActivity, AnggotaActivity::class.java)
//                startActivity(intent)
            }
            rvAnggota.adapter = adapter
            adapter.submitList(task!!.team)
        }
    }

    private fun setupView(task: Task?) {
        with(binding){
            Glide.with(this@DetailTaskActivity)
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
            val layoutManager = LinearLayoutManager(this@DetailTaskActivity)
            rvSubtugas.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@DetailTaskActivity,layoutManager.orientation)
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