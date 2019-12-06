package edu.cs371m.silverscreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListFriendsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var friends= MutableLiveData<List<String>>().apply {
        value = mutableListOf()
    }

    fun observeFriends() : LiveData<List<String>> {
        return friends
    }

    fun updateFriends(updated: List<String>){

        friends.postValue(updated)
    }
}
