package com.otakkanan.taskapp.ui.main.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.databinding.FragmentSettingBinding
import com.otakkanan.taskapp.utils.BaseFragment
import java.util.Locale

class SettingFragment : BaseFragment() {

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

        setupToolbar()
        setupListeners()

        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListeners() {
        // Listener untuk menu pengaturan tugas
        binding.menuPengaturanTugasContainer.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_pengaturanTugasFragment)
        }

        // Listener untuk menu notifikasi
        binding.menuNotifikasiContainer.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_notifikasiFragment)
        }

        // Listener untuk menu bahasa
        binding.menuBahasaContainer.setOnClickListener {
            openLanguageDialog()
        }
    }

    private fun openLanguageDialog() {
        // Dialog bahasa
        val languages = arrayOf(
            "Bahasa Indonesia", "English", "Français", "Deutsch",
            "Español", "Italiano", "Português", "Русский",
            "中文", "日本語", "한국어"
        )

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
            .setTitle("Pilih Bahasa")
            .setNeutralButton("Batal") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setSingleChoiceItems(languages, checkedItem) { _, which ->
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
            .show()
    }

    private fun setLanguage(languageCode: String) {
        // Implementasi pengaturan bahasa
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
