package com.otakkanan.taskapp.ui.goals

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.component.DatePicker
import com.otakkanan.taskapp.databinding.ActivityAddGoalsBinding
import com.otakkanan.taskapp.databinding.ActivityMainBinding
import com.otakkanan.taskapp.databinding.DialogPengingatBinding
import com.otakkanan.taskapp.databinding.FragmentBerandaBinding
import org.w3c.dom.Text

class AddGoalsActivity : AppCompatActivity() {

    private var _binding: ActivityAddGoalsBinding? = null
    private val binding get() = _binding!!

    // Date Picker
    private var selectedDay: Int? = null
    private var selectedMonth: Int? = null
    private var selectedYear: Int? = null

    //Prioritas
    private var prioritas: String = "Default"
    private var rankPrioritas: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAddGoalsBinding.inflate(layoutInflater)
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
            }
        }

        setupTanggalSelesai()
        setupPrioritas()
        setupPrengingat()
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
        val switchTanggalSelesai = binding.swTglSelesai
        val tanggalSelesaiLayout = binding.layoutTglSelesai


        switchTanggalSelesai.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                tanggalSelesaiLayout.visibility = View.VISIBLE
            } else {
                tanggalSelesaiLayout.visibility = View.GONE
                binding.edTglSelesai.setText("")
            }
        }

        binding.btnSelesai.setOnClickListener{
            finish()
        }
    }

    private fun setupPrioritas() {
        val btnPrioritas: CardView = binding.btnPrioritas
        val txtPrioritas: TextView = binding.txtPrioritas

        txtPrioritas.text = "Default"
        txtPrioritas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)  // Tidak ada ikon

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
                        val icon = ContextCompat.getDrawable(this@AddGoalsActivity, R.drawable.flag)
                        icon?.setColorFilter(ContextCompat.getColor(this@AddGoalsActivity, R.color.md_theme_primary), android.graphics.PorterDuff.Mode.SRC_IN)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}