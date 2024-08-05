package com.otakkanan.taskapp.ui.main.kalender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KalenderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is kalender Fragment"
    }
    val text: LiveData<String> = _text
}