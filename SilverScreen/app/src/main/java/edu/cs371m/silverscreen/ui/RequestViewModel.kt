package edu.cs371m.silverscreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var requests = MutableLiveData<List<String>>().apply {
        value = mutableListOf()
    }

    fun observeRequests() : LiveData<List<String>> {
        return requests
    }

    fun updateRequests(updated: List<String>){

        requests.postValue(updated)
    }
}
