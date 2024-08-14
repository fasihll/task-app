package com.otakkanan.taskapp.ui.tugas.tugasbaru

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.component.topsheet.TopSheetBehavior
import com.otakkanan.taskapp.databinding.ActivityTugasBaruBinding
import com.otakkanan.taskapp.databinding.TopSheetTugasBaruBinding
import dagger.hilt.android.AndroidEntryPoint
import com.otakkanan.taskapp.ui.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TugasBaruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTugasBaruBinding
    private lateinit var buttonExpand: ImageView
    private lateinit var topSheetBehavior: TopSheetBehavior<View>

    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]

    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear

    private val dates = ArrayList<Date>()

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


        setupTopSheet()
        setupCalendar()
        setUpCalendar()
    }

    private fun setupTopSheet() {
        topSheetBehavior = TopSheetBehavior.from(binding.topSheetContainer.root)
        buttonExpand = findViewById(R.id.btn_expand)
        buttonExpand.setOnClickListener {
            openTopSheet()
        }
    }

    private fun openTopSheet() {
        buttonExpand = findViewById(R.id.btn_expand)
        topSheetBehavior.state = TopSheetBehavior.STATE_EXPANDED

        topSheetBehavior.setTopSheetCallback(object : TopSheetBehavior.TopSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float, isOpening: Boolean?) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == TopSheetBehavior.STATE_EXPANDED) {
                    // Change the image when the Bottom Sheet is expanded
                    buttonExpand.setOnClickListener {
                        topSheetBehavior.state = TopSheetBehavior.STATE_COLLAPSED
                    }
                    buttonExpand.setImageResource(R.drawable.ic_expand_less_24);
                } else if (newState == TopSheetBehavior.STATE_COLLAPSED) {
                    // Change the image when the Bottom Sheet is collapsed
                    buttonExpand.setOnClickListener {
                        topSheetBehavior.state = TopSheetBehavior.STATE_EXPANDED
                    }
                    buttonExpand.setImageResource(R.drawable.ic_expand_more_24);
                }
            }
        })
    }

    private fun setupCalendar() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)

        lastDayInCalendar.add(Calendar.MONTH, 6)

        setUpCalendar()

        binding.calendarPrevButton.setOnClickListener {
            if (cal.after(currentDate)) {
                cal.add(Calendar.MONTH, -1)
                if (cal == currentDate)
                    setUpCalendar()
                else
                    setUpCalendar(changeMonth = cal)
            }
        }

        binding.calendarNextButton.setOnClickListener {
            if (cal.before(lastDayInCalendar)) {
                cal.add(Calendar.MONTH, 1)
                setUpCalendar(changeMonth = cal)
            }
        }
    }

    private fun setUpCalendar(changeMonth: Calendar? = null) {
        // first part
        binding.calendarRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        ) // Set LayoutManager first
        binding.calendarRecyclerView.adapter = CalendarAdapter(
            this,
            dates,
            Calendar.getInstance(Locale.ENGLISH),
            cal
        ).apply {
            setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    // Handle item click event here
                }
            })
        }
        binding.txtCurrentMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        selectedDay =
            when {
                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
                else -> currentDay
            }
        selectedMonth =
            when {
                changeMonth != null -> changeMonth[Calendar.MONTH]
                else -> currentMonth
            }
        selectedYear =
            when {
                changeMonth != null -> changeMonth[Calendar.YEAR]
                else -> currentYear
            }
        // second part
        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        while (dates.size < maxDaysInMonth) {
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }
}