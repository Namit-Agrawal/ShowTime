package edu.cs371m.silverscreen.ui.trailer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.silverscreen.api.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrailersViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val movieDBApi = MovieDBApi.create()
    private val movieRepo = MovieDBRepository(movieDBApi)
    val queryResult= MutableLiveData<MovieDBPost>().apply {
        value = null
    }

    val movieID = MutableLiveData<Int>().apply {
        value = null
    }

    val videoList= MutableLiveData<videoPost>().apply {
        value = null
    }

    //query movies from db
    fun DBQuery() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        queryResult.postValue(
//            movieRepo.fetchResponse(
//                "2019-12-03",
//                zipcode.value!!,
//                radius.value!!,
//                "7",
//                "bsj768xkm54t6wuchqxxrbrt"
//            )
        movieRepo.fetchResponse()
        )
    }
}