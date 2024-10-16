package com.otakkanan.taskapp.ui.main.beranda.sliders.goals

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
import com.otakkanan.taskapp.data.model.Goal
import com.otakkanan.taskapp.databinding.FragmentBannerGoalsBinding
import com.otakkanan.taskapp.utils.Helper.loadJSONFromAssets
import com.otakkanan.taskapp.utils.Helper.parseGoalsJson

class GoalsBannerFragment : Fragment() {
    private var _binding: FragmentBannerGoalsBinding? = null
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
        _binding = FragmentBannerGoalsBinding.inflate(layoutInflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTaskSliders()
    }

    private fun setupTaskSliders() {
        with(binding){


            val goalsAdapter = GoalsAdapter()
            vpGoalsBanner.adapter = goalsAdapter

            // Membaca file JSON dari assets
            val jsonString = loadJSONFromAssets(requireContext(), "goals_dummy.json")

            // Mengonversi JSON ke List of Goals
            val goalsList: List<Goal> = parseGoalsJson(jsonString)

            goalsAdapter.submitList(goalsList)

            vpGoalsBanner.offscreenPageLimit = 2 // Adjust the padding as needed
            vpGoalsBanner.clipToPadding = false
            vpGoalsBanner.clipChildren = false

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
            vpGoalsBanner.setPageTransformer(transformer)


            val taskIndicator = Array(goalsList.size){ ImageView(requireContext()) }

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

            vpGoalsBanner.registerOnPageChangeCallback(pageChangeListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpGoalsBanner.unregisterOnPageChangeCallback(pageChangeListener)
        _binding= null
    }

}