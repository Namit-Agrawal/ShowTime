package edu.cs371m.silverscreen.ui.theaters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TheatersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is theaters Fragment"
    }
    val text: LiveData<String> = _text
}