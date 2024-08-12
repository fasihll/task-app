package com.otakkanan.taskapp.ui.onboarding

import ViewPagerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textview.MaterialTextView
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.ui.auth.login.LoginActivity
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {

    private val titleList = mutableListOf<String>()
    private val descList = mutableListOf<String>()
    private val imageList = mutableListOf<Int>()
    private var highlightWords = mutableListOf<String>()
    private lateinit var viewPager2: ViewPager2
    private lateinit var indicator3: CircleIndicator3
    private lateinit var nextButton: ImageButton
    private lateinit var skipTextView: MaterialTextView
    // Handler and Runnable for auto-swiping
    private val handler = Handler(Looper.getMainLooper())
    private val autoSwipeRunnable = object : Runnable {
        override fun run() {
            val nextItem = (viewPager2.currentItem + 1) % viewPager2.adapter!!.itemCount
            viewPager2.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 3000) // Adjust the delay as needed (e.g., 3000ms for 3 seconds)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        // Initialize UI components
        viewPager2 = findViewById(R.id.view_pager2)
        indicator3 = findViewById(R.id.indicator)
        nextButton = findViewById(R.id.nextOnBoardingButton)
        skipTextView = findViewById(R.id.skipTextView)

        // Prepare onboarding content
        postToList()

        // Set up ViewPager2 and CircleIndicator3
        viewPager2.adapter = ViewPagerAdapter(imageList, titleList, descList, highlightWords)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicator3.setViewPager(viewPager2)

        // Set click listeners for the buttons
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // "Next" button action
        nextButton.setOnClickListener {
            val nextItem = viewPager2.currentItem + 1
            if (nextItem < viewPager2.adapter!!.itemCount) {
                viewPager2.setCurrentItem(nextItem, true)
            } else {
                completeOnboarding()
            }
        }

        // "Skip" button action
        skipTextView.setOnClickListener {
            completeOnboarding()
        }

        // Start auto-swiping
        handler.postDelayed(autoSwipeRunnable, 7000) // Start after an initial delay
    }

    private fun completeOnboarding() {
        // Mark onboarding as completed in SharedPreferences
        val sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isOnboardingCompleted", true)
        editor.apply()

        // Navigate to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optionally close the OnBoardingActivity
    }

    private fun postToList() {
        addToList("Manajemen Tugas", "Mari ciptakan ruang untuk alur kerja Anda.", R.drawable.on_boarding1, "ruang")
        addToList("Manajemen Tugas", "Bekerja lebih Terstruktur dan Terorganisir \uD83D\uDC4C", R.drawable.on_boarding2, "Terstruktur")
        addToList("Manajemen Tugas", "Kelola tugas Cepat untuk hasil Maksimal ✌", R.drawable.on_boarding3, "Cepat")
    }

    private fun addToList(title: String, desc: String, image: Int, highlightWord: String) {
        titleList.add(title)
        descList.add(desc)
        imageList.add(image)
        highlightWords.add(highlightWord)
    }
}
