package com.otakkanan.taskapp.ui.proyek

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.component.DatePicker
import com.otakkanan.taskapp.databinding.ActivityAddGoalsBinding
import com.otakkanan.taskapp.databinding.ActivityProyekBaruBinding
import com.otakkanan.taskapp.databinding.ActivityTugasBerulangBaruBinding
import com.otakkanan.taskapp.databinding.CalendarDayLayoutBinding
import com.otakkanan.taskapp.databinding.DialogPengingatBinding
import com.otakkanan.taskapp.ui.main.kalender.CalendarAdapter
import com.otakkanan.taskapp.ui.main.kalender.CalendarHeaderAdapter
import com.otakkanan.taskapp.ui.main.kalender.DataAdapter
import com.otakkanan.taskapp.ui.proyek.detail.ProyekTimDetailActivity
import com.otakkanan.taskapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ProyekBaruActivity : AppCompatActivity(),
    DatePicker.OnDateSelectedListener {

        private lateinit var binding: ActivityProyekBaruBinding

        // Date Picker
        private var selectedDay: Int? = null
        private var selectedMonth: Int? = null
        private var selectedYear: Int? = null

        //Prioritas
        private var prioritas: String = "Default"
        private var rankPrioritas: Int = 1

        private val selectedDates = mutableSetOf<LocalDate>()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityProyekBaruBinding.inflate(layoutInflater)
            setContentView(binding.root)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            with(binding){
                btnBack.setOnClickListener{
                    finish()
                }
                btnSelesai.setOnClickListener{
                    finish()
                    startActivity(Intent(this@ProyekBaruActivity,ProyekTimDetailActivity::class.java))
                }
            }

            setupCalenderMonth()
            setupPrioritas()
            setupPrengingat()
            setupTundaTugas()
            setupFrekuensiPengerjaan()
            setupTanggalSelesai()
            setupTanggalMulai()
        }

        private fun setupTanggalMulai() {
            val btnTglMulai = binding.btnTglMulai
            btnTglMulai.setOnClickListener {
                val datePicker = DatePicker()
                if (selectedDay != null && selectedMonth != null && selectedYear != null) {
                    datePicker.setInitialSelectedDate(selectedDay!!, selectedMonth!!, selectedYear!!)
                }
                datePicker.show(supportFragmentManager, "DATE_PICKER")
            }
        }

        private fun setupTanggalSelesai() {
            // Ambil referensi dari Switch dan Layout
            val swTglSelesai = binding.swTglSelesai
            val layoutTglSelesai = binding.layoutTglSelesai

            // Set listener untuk Switch
            swTglSelesai.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // Tampilkan layoutTglSelesai ketika Switch diaktifkan
                    layoutTglSelesai.visibility = View.VISIBLE
                } else {
                    // Sembunyikan layoutTglSelesai ketika Switch dimatikan
                    layoutTglSelesai.visibility = View.GONE
                    clearEditText(4)
                }
            }
        }



        private fun setupPrengingat() {
            val btnPengingat = binding.btnPengingat
            btnPengingat.setOnClickListener {
                val pengingatDialogView = DialogPengingatBinding.inflate(layoutInflater).root
                val dialog = MaterialAlertDialogBuilder(this)
                    .setView(pengingatDialogView)
                    .show()
                val btnOk = pengingatDialogView.findViewById<Button>(R.id.btn_ok)
                val btnCancel = pengingatDialogView.findViewById<Button>(R.id.btn_cancel)
                btnOk.setOnClickListener {
                    dialog.dismiss()
                }

                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        private fun setupPrioritas() {
            val btnPrioritas: CardView = binding.btnPrioritas
            val txtPrioritas: TextView = binding.txtPrioritas

            txtPrioritas.text = "Default"
            txtPrioritas.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                null,
                null
            )  // Tidak ada ikon

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
                        txtPrioritas.text = priorityText

                        // Jika prioritas bukan "Default", tambahkan ikon
                        if (rankPrioritas != 1) {
                            val icon = ContextCompat.getDrawable(
                                this@ProyekBaruActivity,
                                R.drawable.flag
                            )
                            icon?.setColorFilter(
                                ContextCompat.getColor(
                                    this@ProyekBaruActivity,
                                    R.color.md_theme_primary
                                ), android.graphics.PorterDuff.Mode.SRC_IN
                            )
                            txtPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                        } else {
                            txtPrioritas.text = "Default"
                            // Jika kembali ke "Default", hapus ikon
                            txtPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
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
            val checkBoxTundaTugas: MaterialCheckBox = binding.tundaTugasCheckbox
            checkBoxTundaTugas.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(this, "Proyek Ditunda", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Proyek Berjalan", Toast.LENGTH_SHORT).show()
                }

            }
        }

        private fun setupFrekuensiPengerjaan() {
            with(binding) {
                // Set OnCheckedChangeListener untuk RadioGroup
                radioGroupFrekuensi.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.rb_all_day -> {
                            specificDayLayout.visibility = View.GONE
                            specificMonthLayout.visibility = View.GONE
                            specificYearLayout.visibility = View.GONE
                            berulangLayout.visibility = View.GONE
                            unCheckedSpecificDay()
                            clearEditText(0)
                            removeSelectedDate()
                        }

                        R.id.rb_specific_day -> {
                            specificDayLayout.visibility = View.VISIBLE
                            specificMonthLayout.visibility = View.GONE
                            specificYearLayout.visibility = View.GONE
                            berulangLayout.visibility = View.GONE
                            clearEditText(0)
                            removeSelectedDate()
                        }

                        R.id.rb_specific_month -> {
                            specificDayLayout.visibility = View.GONE
                            specificMonthLayout.visibility = View.VISIBLE
                            specificYearLayout.visibility = View.GONE
                            berulangLayout.visibility = View.GONE
                            unCheckedSpecificDay()
                            removeSelectedDate()
                            clearEditText(1)
                        }

                        R.id.rb_specific_year -> {
                            specificDayLayout.visibility = View.GONE
                            specificMonthLayout.visibility = View.GONE
                            specificYearLayout.visibility = View.VISIBLE
                            berulangLayout.visibility = View.GONE
                            unCheckedSpecificDay()
                            removeSelectedDate()
                            clearEditText(2)
                        }

                        R.id.rb_berulang -> {
                            specificDayLayout.visibility = View.GONE
                            specificMonthLayout.visibility = View.GONE
                            specificYearLayout.visibility = View.GONE
                            berulangLayout.visibility = View.VISIBLE
                            unCheckedSpecificDay()
                            removeSelectedDate()
                            clearEditText(3)
                        }

                        else -> {
                            specificDayLayout.visibility = View.GONE
                            specificMonthLayout.visibility = View.GONE
                            specificYearLayout.visibility = View.GONE
                            berulangLayout.visibility = View.GONE
                            unCheckedSpecificDay()
                            removeSelectedDate()
                        }
                    }
                }
            }
        }

    private fun removeSelectedDate() {
        selectedDates.clear()
        setupCalenderMonth()
    }

    private fun setupCalenderMonth() {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth
        val endMonth = currentMonth
        val firstDayOfWeek = firstDayOfWeekFromLocale()

        binding.specificMonthLayout.setup(startMonth, endMonth, firstDayOfWeek)
        binding.specificMonthLayout.scrollToMonth(currentMonth)

        binding.specificMonthLayout.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val dayText = container.textView

                if (day.date.month == currentMonth.month) {
                    dayText.text = day.date.dayOfMonth.toString()
                }else{
                    dayText.visibility = View.GONE
                }


                // Set smaller padding for the selected day to reduce background size
                val paddingValue = resources.getDimensionPixelSize(R.dimen.selected_day_padding) // You can define this dimension in your `dimens.xml`
                // Apply padding (optional: set smaller padding when selected)
                dayText.setPadding(paddingValue, paddingValue, paddingValue, paddingValue)

                if (selectedDates.contains(day.date)) {
                    // Set white text color

                    // Apply the background with rounded corners for selected days
                    dayText.background = ContextCompat.getDrawable(this@ProyekBaruActivity, R.drawable.selected_day_background)
                } else {
                    // Reset the text color to black (or default)

                    dayText.background = null
                }

                dayText.setOnClickListener {
                    // Update the selected dates based on user click
                    if (selectedDates.contains(day.date)) {
                        selectedDates.remove(day.date)
                        dayText.setTextColor(ContextCompat.getColor(this@ProyekBaruActivity, R
                            .color.md_theme_secondary))
                        dayText.background = null
                    } else {
                        selectedDates.add(day.date)
                        dayText.setTextColor(ContextCompat.getColor(this@ProyekBaruActivity, R
                            .color.md_theme_onSecondary))
                        dayText.background = ContextCompat.getDrawable(this@ProyekBaruActivity, R.drawable.selected_day_background)
                    }

                    // Log the selected dates in the desired format
                    val selectedDatesList = selectedDates.toList() // Convert to List to join the dates

                    // Define a date format for logging
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // Format: dd-MM-yyyy

                    // Log the selected dates
                    val formattedDates = selectedDatesList.joinToString(", ") { it.format(formatter) }
                    Log.d("SelectedDates", "Selected Dates: $formattedDates")
                }
            }
        }
    }

    private fun firstDayOfWeekFromLocale(): DayOfWeek {
        // Get the locale of the system or specify a specific locale (e.g., Locale.US)
        val locale = Locale.getDefault()

        // Retrieve the first day of the week based on the system's locale
        val weekFields = WeekFields.of(locale)
        val firstDayOfWeek = weekFields.firstDayOfWeek

        return firstDayOfWeek
    }

    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = view.findViewById<TextView>(R.id.dayText)

        // With ViewBinding
//         val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    }


    private fun unCheckedSpecificDay() {
            with(binding) {
                checkBoxMonday.isChecked = false
                checkBoxTuesday.isChecked = false
                checkBoxWednesday.isChecked = false
                checkBoxThursday.isChecked = false
                checkBoxFriday.isChecked = false
                checkBoxSaturday.isChecked = false
                checkBoxSunday.isChecked = false
            }
        }

        private fun clearEditText(rbs: Int) {
            with(binding) {
                when (rbs) {
                    1 -> {
                        edDateOfYear.setText("")
                        edHariAktivitas.setText("")
                        edHariIstirahat.setText("")
                    }

                    2 -> {
//                        edDateOfMonth.setText("")
                        edHariAktivitas.setText("")
                        edHariIstirahat.setText("")
                    }

                    3 -> {
                        edDateOfYear.setText("")
//                        edDateOfMonth.setText("")
                    }

                    4 -> {
                        edTglSelesai.setText("")
                    }

                    else -> {
                        edDateOfYear.setText("")
//                        edDateOfMonth.setText("")
                        edHariAktivitas.setText("")
                        edHariIstirahat.setText("")
                    }
                }
            }
        }

        override fun onDateSelected(date: String) {
            // Tampilkan tanggal terpilih di UI
            binding.txtTglMulai.text = date
            try {
                val parts = date.split(" ") // Pecah string tanggal menjadi bagian
                selectedDay = parts[0].toInt()

                val sdf = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())
                val parsedDate = sdf.parse(parts[1] + " " + parts[2]) // Format "MMMM yyyy"
                val calendar = Calendar.getInstance().apply { time = parsedDate!! }
                selectedMonth = calendar.get(Calendar.MONTH)
                selectedYear = calendar.get(Calendar.YEAR)
            } catch (e: Exception) {
                e.printStackTrace() // Debug jika format salah
            }
        }
}