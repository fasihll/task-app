package com.otakkanan.taskapp.ui.main.tugas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TugasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tugas Fragment"
    }
    val text: LiveData<String> = _text
}