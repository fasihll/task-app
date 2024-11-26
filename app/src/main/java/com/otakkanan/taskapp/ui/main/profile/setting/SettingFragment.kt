package com.otakkanan.taskapp.ui.main.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentSettingBinding
import java.util.Locale

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private val viewModel: SettingViewModel by viewModels()

    // Deklarasi objek binding
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi binding
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        hideBottomNavigation()
        setupToolbar()

        binding.menuPengaturanTugasContainer.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_pengaturanTugasFragment)
        }

        binding.menuNotifikasiContainer.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_notifikasiFragment)
        }

        binding.menuBahasaContainer.setOnClickListener {
            openLanguageDialog()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        showBottomNavigation()
    }

    private fun hideBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openLanguageDialog() {
        // Daftar bahasa yang dapat dipilih
        val languages = arrayOf(
            "Bahasa Indonesia", "English", "Français", "Deutsch",
            "Español", "Italiano", "Português", "Русский",
            "中文", "日本語", "한국어"
        )

        // Tentukan bahasa default (misalnya Bahasa Indonesia atau bahasa sistem)
        val checkedItem = when (Locale.getDefault().language) {
            "id" -> 0 // Bahasa Indonesia
            "en" -> 1 // English
            "fr" -> 2 // Français
            "de" -> 3 // Deutsch
            "es" -> 4 // Español
            "it" -> 5 // Italiano
            "pt" -> 6 // Português
            "ru" -> 7 // Русский
            "zh" -> 8 // 中文
            "ja" -> 9 // 日本語
            "ko" -> 10 // 한국어
            else -> 0 // Default to Bahasa
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih Bahasa") // Judul dialog
            .setNeutralButton("Batal") { dialog, which ->
                // Respons ketika tombol Batal ditekan
            }
            .setPositiveButton("OK") { dialog, which ->
                // Respons ketika tombol OK ditekan
            }
            // Pilihan tunggal (single choice), dimulai dengan item yang terpilih
            .setSingleChoiceItems(languages, checkedItem) { dialog, which ->
                // Respons ketika item dipilih
                when (which) {
                    0 -> setLanguage("id") // Bahasa Indonesia dipilih
                    1 -> setLanguage("en") // Bahasa Inggris dipilih
                    2 -> setLanguage("fr") // Français dipilih
                    3 -> setLanguage("de") // Deutsch dipilih
                    4 -> setLanguage("es") // Español dipilih
                    5 -> setLanguage("it") // Italiano dipilih
                    6 -> setLanguage("pt") // Português dipilih
                    7 -> setLanguage("ru") // Русский dipilih
                    8 -> setLanguage("zh") // 中文 dipilih
                    9 -> setLanguage("ja") // 日本語 dipilih
                    10 -> setLanguage("ko") // 한국어 dipilih
                }
            }
            .show() // Tampilkan dialog
    }

    private fun setLanguage(languageCode: String) {
        // Membuat Locale baru berdasarkan kode bahasa
        val locale = Locale(languageCode)
        Locale.setDefault(locale) // Set Locale default aplikasi

        // Membuat dan mengubah konfigurasi sumber daya aplikasi
        val config = resources.configuration
        config.setLocale(locale)

//        // Mengupdate konfigurasi dengan konteks baru
//        createConfigurationContext(config)
//
//        // Memanggil ulang aktivitas untuk menerapkan perubahan bahasa
//        val intent = Intent(applicationContext, MainActivity::class.java) // Ganti dengan activity yang sesuai
//        startActivity(intent) // Restart activity untuk melihat perubahan
//        finish() // Tutup activity lama
    }
}
