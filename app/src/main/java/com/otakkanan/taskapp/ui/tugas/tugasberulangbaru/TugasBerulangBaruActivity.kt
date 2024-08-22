package com.otakkanan.taskapp.ui.tugas.tugasberulangbaru

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.component.topsheet.TopSheetBehavior
import com.otakkanan.taskapp.databinding.ActivityTugasBerulangBaruBinding

class TugasBerulangBaruActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTugasBerulangBaruBinding
    //Tunda Tugas
    private var tundaTugas: Boolean = false

    //Prioritas
    private var prioritas: String = "Default"
    private var rankPrioritas: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTugasBerulangBaruBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupTopBar()
        setupPrioritas()
        setupTundaTugas()
        setupFrekuensiPengerjaan()
        setupTanggalSelesai()
    }

    private fun setupTanggalSelesai() {
        // Ambil referensi dari Switch dan Layout
        val switchTanggalSelesai = binding.switchTanggalSelesai
        val tanggalSelesaiLayout = binding.tanggalSelesaiLayout

        // Set listener untuk Switch
        switchTanggalSelesai.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Tampilkan tanggalSelesaiLayout ketika Switch diaktifkan
                tanggalSelesaiLayout.visibility = View.VISIBLE
            } else {
                // Sembunyikan tanggalSelesaiLayout ketika Switch dimatikan
                tanggalSelesaiLayout.visibility = View.GONE
                clearEditText(4)
            }
        }
    }


    private fun setupTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.selesai -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupPrioritas() {
        val btnPrioritas: Button = binding.btnPrioritas

        btnPrioritas.text = "Default"
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
                        val icon = ContextCompat.getDrawable(this@TugasBerulangBaruActivity, R.drawable.flag)
                        icon?.setColorFilter(ContextCompat.getColor(this@TugasBerulangBaruActivity, R.color.md_theme_primary), android.graphics.PorterDuff.Mode.SRC_IN)
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
        val checkBoxTundaTugas: ImageView = binding.tundaTugasCheckbox
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
                    }
                    R.id.rb_specific_day -> {
                        specificDayLayout.visibility = View.VISIBLE
                        specificMonthLayout.visibility = View.GONE
                        specificYearLayout.visibility = View.GONE
                        berulangLayout.visibility = View.GONE
                        clearEditText(0)
                    }
                    R.id.rb_specific_month -> {
                        specificDayLayout.visibility = View.GONE
                        specificMonthLayout.visibility = View.VISIBLE
                        specificYearLayout.visibility = View.GONE
                        berulangLayout.visibility = View.GONE
                        unCheckedSpecificDay()
                        clearEditText(1)
                    }
                    R.id.rb_specific_year -> {
                        specificDayLayout.visibility = View.GONE
                        specificMonthLayout.visibility = View.GONE
                        specificYearLayout.visibility = View.VISIBLE
                        berulangLayout.visibility = View.GONE
                        unCheckedSpecificDay()
                        clearEditText(2)
                    }
                    R.id.rb_berulang -> {
                        specificDayLayout.visibility = View.GONE
                        specificMonthLayout.visibility = View.GONE
                        specificYearLayout.visibility = View.GONE
                        berulangLayout.visibility = View.VISIBLE
                        unCheckedSpecificDay()
                        clearEditText(3)
                    }
                    else -> {
                        specificDayLayout.visibility = View.GONE
                        specificMonthLayout.visibility = View.GONE
                        specificYearLayout.visibility = View.GONE
                        berulangLayout.visibility = View.GONE
                        unCheckedSpecificDay()
                    }
                }
            }
        }
    }
    private fun unCheckedSpecificDay(){
        with(binding){
            checkBoxMonday.isChecked = false
            checkBoxTuesday.isChecked = false
            checkBoxWednesday.isChecked = false
            checkBoxThursday.isChecked = false
            checkBoxFriday.isChecked = false
            checkBoxSaturday.isChecked = false
            checkBoxSunday.isChecked = false
        }
    }

    private fun clearEditText(rbs: Int){
        with(binding){
            when(rbs){
                1 -> {
                    edDateOfYear.setText("")
                    edHariAktivitas.setText("")
                    edHariIstirahat.setText("")
                }
                2 -> {
                    edDateOfMonth.setText("")
                    edHariAktivitas.setText("")
                    edHariIstirahat.setText("")
                }
                3 -> {
                    edDateOfYear.setText("")
                    edDateOfMonth.setText("")
                }
                4 -> {
                    edTglSelesaiTugasBerulang.setText("")
                }
                else -> {
                    edDateOfYear.setText("")
                    edDateOfMonth.setText("")
                    edHariAktivitas.setText("")
                    edHariIstirahat.setText("")
                }
            }
        }
    }

}