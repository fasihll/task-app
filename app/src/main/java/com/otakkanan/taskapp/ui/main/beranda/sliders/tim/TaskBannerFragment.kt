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
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.FragmentBannerTaskBinding
import com.otakkanan.taskapp.utils.Helper

class TaskBannerFragment : Fragment() {
    private var _binding: FragmentBannerTaskBinding? = null
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
        _binding = FragmentBannerTaskBinding.inflate(layoutInflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTaskSliders()
    }

    private fun setupTaskSliders() {
            with(binding){
                // Membaca file JSON dari assets
                val jsonString = Helper.loadJSONFromAssets(requireContext(), "task_dummy.json")

                // Mengonversi JSON ke List of Goals
                val taskList: List<Task> = Helper.parseTaskJson(jsonString)

                val taskAdapter = TaskAdapter()
                vpTimBanner.adapter = taskAdapter
                taskAdapter.submitList(taskList)

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


                val taskIndicator = Array(taskList.size){ ImageView(requireContext()) }

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