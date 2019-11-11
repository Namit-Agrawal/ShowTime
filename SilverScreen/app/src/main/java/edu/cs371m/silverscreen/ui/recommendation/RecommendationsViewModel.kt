package edu.cs371m.silverscreen.ui.recommendation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecommendationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is recommendations Fragment"
    }
    val text: LiveData<String> = _text
}