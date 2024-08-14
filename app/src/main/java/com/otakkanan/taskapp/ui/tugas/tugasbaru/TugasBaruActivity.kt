package com.otakkanan.taskapp.ui.tugas.tugasbaru

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.ActivityTugasBaruBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TugasBaruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTugasBaruBinding
    private var isExpanded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTugasBaruBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupExpandButton()
    }

    private fun setupExpandButton() {
        binding.btnExpand.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                binding.expandDetail.visibility = View.VISIBLE
                 binding.btnExpand.setImageResource(R.drawable.ic_expand_less_24)
            } else {
                binding.expandDetail.visibility = View.GONE
                 binding.btnExpand.setImageResource(R.drawable.ic_expand_more_24)
            }
        }
    }

}