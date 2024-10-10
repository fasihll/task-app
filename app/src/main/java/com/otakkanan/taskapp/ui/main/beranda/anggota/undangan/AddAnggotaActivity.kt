package com.otakkanan.taskapp.ui.main.beranda.anggota.undangan

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.ActivityAddAnggotaBinding

class AddAnggotaActivity : AppCompatActivity() {

    private var _binding: ActivityAddAnggotaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAddAnggotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val total: Int = intent.getIntExtra(TOTAL, 0)
        val task: Task? = intent.getParcelableExtra(TAG)


        binding.btnBack.setOnClickListener { finish() }
        binding.titleCountAnggota.text = getString(R.string.title_page_tambah_anggota, total.toString())

        setupRecyclerview(task)
    }

    private fun setupRecyclerview(task: Task?) {

        with(binding) {
            val layoutManager = LinearLayoutManager(
                this@AddAnggotaActivity, LinearLayoutManager
                    .VERTICAL, false
            )
            rvCehckboxAnggota.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@AddAnggotaActivity, layoutManager.orientation)
            rvCehckboxAnggota.addItemDecoration(itemDecoration)

            val adapter = AddAnggotaAdapter()
            rvCehckboxAnggota.adapter = adapter
            adapter.submitList(task!!.team)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val TAG = "AddAnggotaActivity"
        val TOTAL = "TotalAnggota"
    }
}