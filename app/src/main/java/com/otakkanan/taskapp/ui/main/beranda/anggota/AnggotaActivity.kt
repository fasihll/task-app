package com.otakkanan.taskapp.ui.main.beranda.anggota

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.ActivityAnggotaBinding

class AnggotaActivity : AppCompatActivity() {

    private var _binding: ActivityAnggotaBinding? = null
    private  val binding get() = _binding!!
    private lateinit var adapter: FragmentPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAnggotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val task: Task? = intent.getParcelableExtra(TAG)

        binding.btnBack.setOnClickListener(){
            finish()
        }

        binding.countAnggota.text = getString(R.string.anggota_counts, task?.team!!.size.toString())


       setupViewPager(task)


    }

    private fun setupViewPager(task: Task) {
        adapter = FragmentPageAdapter(supportFragmentManager,lifecycle,task)

        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("Partisipan"))
            tabLayout.addTab(tabLayout.newTab().setText("Undangan"))

            viewpager2.adapter = adapter
            tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(p0: TabLayout.Tab?) {
                    if (p0 != null){
                        viewpager2.currentItem = p0.position
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {

                }

                override fun onTabReselected(p0: TabLayout.Tab?) {

                }

            })

            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))

                    if (position == 1) {
                        fadeOut(binding.btnLeave)
                        fadeIn(binding.btnSelesai)
                    } else {
                        fadeIn(binding.btnLeave)
                        fadeOut(binding.btnSelesai)
                    }
                }
            })
        }
    }

    private fun fadeIn(view: View, duration: Long = 300) {
        view.apply {
            alpha = 0f // Mulai dari transparan
            visibility = View.VISIBLE // Set jadi terlihat
            animate()
                .alpha(1f) // Ubah ke opacity penuh
                .setDuration(duration)
                .start()
        }
    }

    // Fungsi untuk animasi fadeOut
    private fun fadeOut(view: View, duration: Long = 300) {
        view.animate()
            .alpha(0f) // Ubah ke transparan
            .setDuration(duration)
            .withEndAction {
                view.visibility = View.GONE // Sembunyikan setelah animasi selesai
            }
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        val TAG = "AnggotaActivity"
    }
}