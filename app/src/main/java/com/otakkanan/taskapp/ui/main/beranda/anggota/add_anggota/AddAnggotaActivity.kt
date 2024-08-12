package com.otakkanan.taskapp.ui.main.beranda.anggota.add_anggota

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.ActivityAddAnggotaBinding
import com.otakkanan.taskapp.databinding.ActivityListAnggotaBinding

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
    }
}