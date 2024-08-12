package com.otakkanan.taskapp.ui.main.beranda.anggota.list_anggota

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.ActivityDetailTaskBinding
import com.otakkanan.taskapp.databinding.ActivityListAnggotaBinding
import com.otakkanan.taskapp.ui.main.beranda.detail_task.DetailTaskActivity

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
        binding.countAnggota.text = "${task?.team!!.size} Anggota"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        val TAG = "ListAnggotaActivity"
    }
}