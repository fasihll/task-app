package com.otakkanan.taskapp.ui.main.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Manajer
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import com.otakkanan.taskapp.ui.tugas.tugasbaru.TugasBaruActivity
import java.time.LocalTime

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private  val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
    ).apply {
        setMargins(0,0,13,0)
    }

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

        setupViewPager2()
        setupTaskDayRecyclerview()
        buttonToTugasBaru()

        return root
    }

    private fun setupViewPager2() {
        with(binding){
            val tasksList = arrayListOf(
                Task(
                    title = "Desain \nUI",
                    deskripsi = "Antarmuka Pengguna (UI) adalah titik interaksi antara manusia dan mesin dalam perangkat lunak atau perangkat keras, memungkinkan pengguna berinteraksi dengan sistem secara efisien.",
                    manajer = listOf(
                        Manajer(name = "Budi Nam",image = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    ),
                    progress = 40,
                    team = listOf(
                        Team(name = "Fasih", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Admin"),
                        Team(name = "Budi Nam", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Manager"),
                        Team(name = "Stephany S", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Admin"),
                        Team(name = "Wen Anita", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "User"),
                    ),
                ),
                Task(
                    title = "Tugas \nLaravel",
                    deskripsi = "Tugas pengembangan aplikasi Laravel meliputi pengaturan lingkungan pengembangan, desain database dan migrasi, pengembangan fitur backend seperti CRUD, autentikasi, dan otorisasi, serta pengaturan routing dan middleware. Selain itu, pengembang juga mengintegrasikan frontend dengan backend menggunakan Blade templating engine untuk membangun antarmuka pengguna yang interaktif.",
                    manajer = listOf(
                        Manajer(name = "Fasih Lisan",image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    ),
                    progress = 70,
                    team = listOf(
                        Team(name = "Fasih", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Manager"),
                        Team(name = "Marzuki", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Admin"),
                        Team(name = "Ahnaf", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Admin"),
                        Team(name = "Suep", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "User"),
                        Team(name = "Budi Nam", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "User")
                    )
                ),
                Task(
                    title = "Tugas \nMobile App",
                    deskripsi = "Tugas pengembangan aplikasi mobile mencakup perancangan antarmuka pengguna (UI/UX), pengembangan fitur aplikasi menggunakan bahasa pemrograman seperti Kotlin atau Swift, integrasi dengan API atau layanan backend, serta pengujian dan debugging untuk memastikan performa dan fungsionalitas aplikasi yang optimal. Selain itu, pengembang juga bertanggung jawab atas pengelolaan penyimpanan data, optimisasi aplikasi untuk berbagai perangkat, serta implementasi keamanan dan pengiriman aplikasi ke toko aplikasi seperti Google Play atau App Store.",
                    manajer = listOf(
                        Manajer(name = "Fasih Lisan",image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    ),
                    progress = 50,
                    team = listOf(
                        Team(name = "Fasih", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Admin"),
                        Team(name = "Marzuki", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Manager"),
                    )
                ),
                Task(
                    title = "Tugas \nCloud Service",
                    deskripsi = "Tugas pengembangan layanan cloud meliputi perancangan, implementasi, dan pemeliharaan infrastruktur cloud, termasuk pengaturan dan pengelolaan server virtual, penyimpanan data, dan jaringan. Pengembang juga bertanggung jawab untuk mengkonfigurasi dan mengelola layanan seperti database, keamanan, dan autentikasi, serta memastikan skalabilitas dan ketersediaan aplikasi melalui otomatisasi dan orkestrasi. Selain itu, tugas ini mencakup pemantauan performa, backup, pemulihan data, serta optimisasi biaya penggunaan layanan cloud.",
                    manajer = listOf(
                        Manajer(name = "Budi Nam",image = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                    ),
                    progress = 10,
                    team = listOf(
                        Team(name = "Fasih", image = "https://images.unsplash" +
                                ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                "&fit=crop&ixlib=rb-4.0" +
                                ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            role = "Manager"),
                    )
                ),
            )

            val taskAdapter = TaskAdapter()
            vpTaskBanner.adapter = taskAdapter
            taskAdapter.submitList(tasksList)

            vpTaskBanner.offscreenPageLimit = 2 // Adjust the padding as needed
            vpTaskBanner.clipToPadding = false
            vpTaskBanner.clipChildren = false

            val transformer = CompositePageTransformer()
            transformer.addTransformer(MarginPageTransformer(40))
            transformer.addTransformer { page, position ->
                val pageWidth = page.width
                val offset = -0.34f // seberapa keliatan item kedua

                // Apply scaling factor, adjusting for smaller screens
                val scaleFactor = Math.max(0.85f, 1 - Math.abs(position) * 0.2f) // Mengurangi faktor skala saat position semakin besar
                page.scaleY = scaleFactor

                // Apply translation to show the next card slightly
                val maxTranslation = 2 * offset * pageWidth + pageWidth
                page.translationX = -position * maxTranslation
            }
            vpTaskBanner.setPageTransformer(transformer)


            val taskIndicator = Array(tasksList.size){ ImageView(requireContext()) }

            taskIndicator.forEach {
                it.setImageResource(
                    R.drawable.nonactive_indicator_dot
                )
                vpIndicator.addView(it,params)
            }
            //default indicator
            taskIndicator[0].setImageResource(R.drawable.active_indicator_dot)

            pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    taskIndicator.mapIndexed { index, imageView ->
                        if (position == index){
                            imageView.setImageResource(R.drawable.active_indicator_dot)
                        }else{
                            imageView.setImageResource(R.drawable.nonactive_indicator_dot)
                        }
                    }
                }
            }

            vpTaskBanner.registerOnPageChangeCallback(pageChangeListener)
        }
    }

    private fun setupTaskDayRecyclerview() {
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
            navigateToTugasBaru()
        }
    }

    private fun navigateToTugasBaru() {
        startActivity(Intent(requireContext(), TugasBaruActivity::class.java))
        requireActivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.vpTaskBanner.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }
}