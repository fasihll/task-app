package com.otakkanan.taskapp.ui.main.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BerandaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is beranda Fragment"
    }
    val text: LiveData<String> = _text
}