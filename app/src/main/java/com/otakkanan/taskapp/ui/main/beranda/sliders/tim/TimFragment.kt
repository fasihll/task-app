package com.otakkanan.taskapp.ui.main.beranda.sliders.tim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.Manajer
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import com.otakkanan.taskapp.databinding.FragmentTimBinding

class TimFragment : Fragment() {
    private var _binding: FragmentTimBinding? = null
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimBinding.inflate(layoutInflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTaskSliders()
    }

    private fun setupTaskSliders() {
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
                                role = "Admin", telp = "+62 853 3607 6077"),
                            Team(name = "Budi Nam", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "Manager", telp = "+62 853 3607 6077"),
                            Team(name = "Stephany S", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "Admin", telp = "+62 853 3607 6077"),
                            Team(name = "Wen Anita", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                    "&fit=crop&ixlib=rb-4.0" +
                                    ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "User", telp = "+62 853 3607 6077"),
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
                                role = "Manager", telp = "+62 853 3607 6077"),
                            Team(name = "Marzuki", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "Admin", telp = "+62 853 3607 6077"),
                            Team(name = "Ahnaf", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "Admin", telp = "+62 853 3607 6077"),
                            Team(name = "Suep", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format" +
                                    "&fit=crop&ixlib=rb-4.0" +
                                    ".3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "User", telp = "+62 853 3607 6077"),
                            Team(name = "Budi Nam", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "User", telp = "+62 853 3607 6077")
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
                                role = "Admin", telp = "+62 853 3607 6077"),
                            Team(name = "Marzuki", image = "https://images.unsplash" +
                                    ".com/photo-1500648767791-00dcc994a43e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                role = "Manager", telp = "+62 853 3607 6077"),
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
                                role = "Manager", telp = "+62 853 3607 6077"),
                        )
                    ),
                )

                val taskAdapter = TaskAdapter()
                vpTimBanner.adapter = taskAdapter
                taskAdapter.submitList(tasksList)

                vpTimBanner.offscreenPageLimit = 2 // Adjust the padding as needed
                vpTimBanner.clipToPadding = false
                vpTimBanner.clipChildren = false

                val transformer = CompositePageTransformer()
                transformer.addTransformer(MarginPageTransformer(40))
                transformer.addTransformer { page, position ->
                    val pageWidth = page.width
                    val offset = -0.34f // seberapa keliatan item kedua

                    // Apply scaling factor, adjusting for smaller screens
                    val scaleFactor = Math.max(0.100f, 1 - Math.abs(position) * 0.2f) // Mengurangi
                    // faktor skala saat position semakin besar
                    page.scaleY = scaleFactor

                    // Apply translation to show the next card slightly
                    val maxTranslation = 2 * offset * pageWidth + pageWidth
                    page.translationX = -position * maxTranslation
                }
                vpTimBanner.setPageTransformer(transformer)


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

                vpTimBanner.registerOnPageChangeCallback(pageChangeListener)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpTimBanner.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }

}