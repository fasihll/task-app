package com.otakkanan.taskapp.ui.main.beranda.anggota.list_anggota

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.ActivityListAnggotaBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.add_anggota.AddAnggotaActivity

class ListAnggotaActivity : AppCompatActivity() {

    private var _binding: ActivityListAnggotaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityListAnggotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val task: Task? = intent.getParcelableExtra(ListAnggotaActivity.TAG)

        binding.btnBack.setOnClickListener(){
            finish()
        }

        binding.btnAddAnggota.setOnClickListener{
            val intent = Intent(this@ListAnggotaActivity,AddAnggotaActivity::class.java)
            intent.putExtra("TotalAnggota",task?.team!!.size)
            intent.putExtra(AddAnggotaActivity.TAG,task)
            startActivity(intent)
        }

        binding.countAnggota.text = "${task?.team!!.size} Anggota"

        setupRecylerview(task)
    }

    private fun setupRecylerview(task: Task) {
       with(binding){
           val layoutManager = LinearLayoutManager(this@ListAnggotaActivity)
           rvListAnggota.layoutManager = layoutManager
           val itemDecoration = DividerItemDecoration(this@ListAnggotaActivity,layoutManager.orientation)
           rvListAnggota.addItemDecoration(itemDecoration)

           val adapter = ListAnggotaAdapter()
           rvListAnggota.adapter = adapter
           adapter.submitList(task.team)
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        val TAG = "ListAnggotaActivity"
    }
}