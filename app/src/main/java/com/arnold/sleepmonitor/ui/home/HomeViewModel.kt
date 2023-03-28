package com.arnold.sleepmonitor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _homeText = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val homeText: LiveData<String> = _homeText

    private val _lightText = MutableLiveData<String>().apply {
        value = "This is light sensor field"
    }
    val lightText: LiveData<String> = _lightText

    private val _accText = MutableLiveData<String>().apply {
        value = "This is acceleration sensor field"
    }
    val accText: LiveData<String> = _accText

    private val _voiceText = MutableLiveData<String>().apply {
        value = "This is voice sensor field"
    }
    val voiceText: LiveData<String> = _voiceText
}