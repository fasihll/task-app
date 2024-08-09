package com.otakkanan.taskapp.ui.tugas.tugasbaru

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.ActivityTugasBaruBinding
import com.otakkanan.taskapp.ui.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TugasBaruActivity : AppCompatActivity(), TopSheetFragment.ButtonClickCompleteListener {

    private lateinit var binding: ActivityTugasBaruBinding
    private var isExpanded: Boolean = false
    private var isTopSheetVisible: Boolean = false
    private val rotation = 0f
    private var fragment: TopSheetFragment? = null

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

        //Top Sheet
        fragment = TopSheetFragment()
        fragment?.let { frag ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit()
            frag.setListener(this)
        }
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                binding.icon.animate().rotationBy(180f)
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }
        })

//        setupTopSheetButton()
        setupCalendar()
        setUpCalendar()
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
        binding.calendarRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // Set LayoutManager first
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

    override fun onResume() {
        super.onResume()
        fragment?.setListener(this)
    }

    override fun onButtonClicked() {
        // set the state to initial state after button click from Top sheet fragment
        binding.motionLayout.transitionToStart()
    }

//    private fun setupTopSheetButton() {
//        binding.btnExpand.setOnClickListener {
//            isTopSheetVisible = !isTopSheetVisible
//            if (isTopSheetVisible) {
//                showTopSheet()
//                binding.btnExpand.setImageResource(R.drawable.ic_expand_less_24)
//            } else {
//                binding.topSheetContainer.visibility = View.GONE
//                binding.btnExpand.setImageResource(R.drawable.ic_expand_more_24)
//            }
//        }
//    }
//
//    private fun showTopSheet() {
//        val inflater = LayoutInflater.from(this)
//        val topSheetView =
//            inflater.inflate(R.layout.top_sheet_tugas_baru, binding.topSheetContainer, false)
//        binding.topSheetContainer.removeAllViews()
//        binding.topSheetContainer.addView(topSheetView)
//        binding.topSheetContainer.visibility = View.VISIBLE
//    }
}