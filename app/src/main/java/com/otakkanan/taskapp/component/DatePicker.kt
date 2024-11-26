package com.otakkanan.taskapp.component

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import java.text.SimpleDateFormat
import java.util.*

class DatePicker : DialogFragment() {

    private lateinit var tvMonthYear: TextView
    private lateinit var calendarGrid: GridLayout
    private var calendar = Calendar.getInstance()
    private var selectedDay: Int? = null
    private var initialSelectedDay: Int? = null
    private var initialSelectedMonth: Int? = null
    private var initialSelectedYear: Int? = null

    fun setInitialSelectedDate(day: Int, month: Int, year: Int) {
        initialSelectedDay = day
        initialSelectedMonth = month
        initialSelectedYear = year
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_date_picker, null)

        builder.setView(view)

        tvMonthYear = view.findViewById(R.id.tv_month_year)
        calendarGrid = view.findViewById(R.id.calendar_grid)

        view.findViewById<ImageButton>(R.id.btn_prev_month).setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        view.findViewById<ImageButton>(R.id.btn_next_month).setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }

        if (initialSelectedDay != null && initialSelectedMonth != null && initialSelectedYear != null) {
            calendar.set(Calendar.DAY_OF_MONTH, initialSelectedDay!!)
            calendar.set(Calendar.MONTH, initialSelectedMonth!!)
            calendar.set(Calendar.YEAR, initialSelectedYear!!)
            selectedDay = initialSelectedDay
        }

        updateCalendar()

        return builder.create()
    }

    private fun updateCalendar() {
        val dateFormat = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
        tvMonthYear.text = dateFormat.format(calendar.time)

        calendarGrid.removeAllViews()

        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Hitung jumlah hari di bulan sebelumnya
        val prevMonthCalendar = calendar.clone() as Calendar
        prevMonthCalendar.add(Calendar.MONTH, -1)
        val daysInPrevMonth = prevMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Tambahkan tanggal dari bulan sebelumnya
        for (i in 0 until startDayOfWeek) {
            val day = daysInPrevMonth - startDayOfWeek + 1 + i
            val dayView = TextView(requireContext()).apply {
                text = day.toString()
                gravity = Gravity.CENTER
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                setPadding(8, 8, 8, 8)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_secondaryContainer)) // Warna untuk bulan sebelumnya
            }
            calendarGrid.addView(dayView)
        }

        // Tambahkan tanggal bulan saat ini
        for (day in 1..daysInMonth) {
            val dayView = TextView(requireContext()).apply {
                text = day.toString()
                gravity = Gravity.CENTER
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                setPadding(8, 8, 8, 8)

                // Highlight tanggal yang dipilih
                if (selectedDay == day) {
                    setBackgroundResource(R.drawable.bg_selected_date)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_background))
                } else {
                    setBackgroundResource(android.R.color.transparent)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_onBackground))
                }

                setOnClickListener {
                    selectedDay = day
                    initialSelectedDay = day
                    initialSelectedMonth = calendar.get(Calendar.MONTH)
                    initialSelectedYear = calendar.get(Calendar.YEAR)

                    updateCalendar()

                    // Kirim tanggal terpilih ke activity
                    val selectedDate = "$day ${tvMonthYear.text}"
                    (activity as? OnDateSelectedListener)?.onDateSelected(selectedDate)
//                    dismiss()
                }
            }
            calendarGrid.addView(dayView)
        }

        // Hitung jumlah slot kosong setelah tanggal terakhir bulan ini
        val totalSlots = 42 // 6 minggu x 7 hari
        val remainingSlots = totalSlots - (startDayOfWeek + daysInMonth)

        // Tambahkan tanggal dari bulan berikutnya
        for (i in 1..remainingSlots) {
            val dayView = TextView(requireContext()).apply {
                text = i.toString()
                gravity = Gravity.CENTER
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                setPadding(8, 8, 8, 8)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_onSecondary)) // Warna untuk bulan berikutnya
            }
            calendarGrid.addView(dayView)
        }
    }



    interface OnDateSelectedListener {
        fun onDateSelected(date: String)
    }
}
