package com.otakkanan.taskapp.ui.tugas.tugasbaru

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.otakkanan.taskapp.component.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.component.topsheet.TopSheetBehavior
import com.otakkanan.taskapp.databinding.ActivityTugasBaruBinding
import com.otakkanan.taskapp.ui.adapter.CalendarAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class TugasBaruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTugasBaruBinding

    // Top Sheet
    private lateinit var buttonExpand: ImageView
    private lateinit var topSheetBehavior: TopSheetBehavior<View>

    //Calendar
    private val lastDayInCalendar = Calendar.getInstance(Locale("id", "ID"))
    private val sdf = SimpleDateFormat("MMMM", Locale("id", "ID"))
    private val cal = Calendar.getInstance(Locale("id", "ID"))

    private val currentDate = Calendar.getInstance(Locale("id", "ID"))
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]

    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear

    private val dates = ArrayList<Date>()

    //Tunda Tugas
    private var tundaTugas: Boolean = false

    //Prioritas
    private var prioritas: String = "Default"
    private var rankPrioritas: Int = 1

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
        setupTimePicker()
        setupTundaTugas()
        setupPrioritas()
        setupTopBar()
    }

    private fun setupTopBar() {
        binding.topAppBar.apply {
            setNavigationOnClickListener {
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.selesai -> {
                        finish()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupPrioritas() {
        topSheetBehavior = TopSheetBehavior.from(binding.topSheetContainer.root)
        val btnPrioritas: Button = findViewById(R.id.btn_prioritas)
// Set nilai awal prioritas
        btnPrioritas.text = "Default"  // Nilai awal
        btnPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)  // Tidak ada ikon

        btnPrioritas.setOnClickListener {
            val prioritasDialogView = layoutInflater.inflate(R.layout.dialog_prioritas, null)

            rankPrioritas = 1

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(prioritasDialogView)
                .show()

            with(prioritasDialogView) {
                val tvPriorityCount: Button = findViewById(R.id.tv_priority_count)

                // Set fungsi tombol kurang
                prioritasDialogView.findViewById<Button>(R.id.btn_min).setOnClickListener {
                    if (rankPrioritas > 1) {  // Batas minimum 1
                        rankPrioritas--
                        tvPriorityCount.setText(rankPrioritas.toString())
                    }
                }

                // Set fungsi tombol tambah
                prioritasDialogView.findViewById<Button>(R.id.btn_plus).setOnClickListener {
                    if (rankPrioritas < 5) {  // Batas maksimum 5 (misalnya)
                        rankPrioritas++
                        tvPriorityCount.setText(rankPrioritas.toString())
                    }
                }

                findViewById<View>(R.id.btn_ok).setOnClickListener {
                    // Update teks btnPrioritas dengan nilai prioritas yang dipilih
                    val priorityText = "$rankPrioritas"
                    btnPrioritas.text = priorityText

                    // Jika prioritas bukan "Default", tambahkan ikon
                    if (rankPrioritas != 1) {
                        val icon = ContextCompat.getDrawable(this@TugasBaruActivity, R.drawable.flag)
                        icon?.setColorFilter(ContextCompat.getColor(this@TugasBaruActivity, R.color.md_theme_primary), android.graphics.PorterDuff.Mode.SRC_IN)
                        btnPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                    } else {
                        btnPrioritas.text = "Default"
                        // Jika kembali ke "Default", hapus ikon
                        btnPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    }

                    dialog.dismiss()
                }

                findViewById<View>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }



    private fun setupTundaTugas() {
        topSheetBehavior = TopSheetBehavior.from(binding.topSheetContainer.root)
        val checkBoxTundaTugas: ImageView = findViewById(R.id.tunda_tugas_checkbox)
        checkBoxTundaTugas.setOnClickListener {
            if (tundaTugas) {
                checkBoxTundaTugas.setImageResource(R.drawable.unchecked_task)
                checkBoxTundaTugas.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_secondaryContainer), android.graphics.PorterDuff.Mode.SRC_IN)
                tundaTugas = false
            } else {
                checkBoxTundaTugas.setImageResource(R.drawable.checked_task)
                checkBoxTundaTugas.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_primary), android.graphics.PorterDuff.Mode.SRC_IN)
                tundaTugas = true
            }
        }
    }

    private fun setupTimePicker() {
        binding.pilihWaktuPicker.setTimeChangedListener(object : TimePicker.TimeChangeListener {
            override fun onTimeChanged(hour: Int, minute: Int) {
                binding.tvHour.text = hour.toString()
                binding.tvMinute.text = minute.toString().padStart(2, '0')
            }
        })
        binding.tvHour.setOnClickListener {
            binding.pilihWaktuPicker.clockMode = TimePicker.ClockMode.HOUR
        }

        binding.tvMinute.setOnClickListener {
            binding.pilihWaktuPicker.clockMode = TimePicker.ClockMode.MINUTE
        }

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
                    buttonExpand.setImageResource(R.drawable.ic_expand_less_24)
                } else if (newState == TopSheetBehavior.STATE_COLLAPSED) {
                    // Change the image when the Bottom Sheet is collapsed
                    buttonExpand.setOnClickListener {
                        topSheetBehavior.state = TopSheetBehavior.STATE_EXPANDED
                    }
                    buttonExpand.setImageResource(R.drawable.ic_expand_more_24)
                }
            }
        })
    }

    private fun setupCalendar() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)

//        lastDayInCalendar.add(Calendar.MONTH, 6)

        setUpCalendar()

        binding.calendarPrevButton.setOnClickListener {
            if (cal.after(currentDate)) {
                cal.add(Calendar.MONTH, -1)
//                if (cal == currentDate)
//                    setUpCalendar()
//                else
                    setUpCalendar(changeMonth = cal)
            }
        }

        binding.calendarNextButton.setOnClickListener {
//            if (cal.before(lastDayInCalendar)) {
                cal.add(Calendar.MONTH, 1)
                setUpCalendar(changeMonth = cal)
//            }
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
            Calendar.getInstance(Locale("id", "ID")),
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