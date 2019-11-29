package edu.cs371m.silverscreen.ui.movie_times

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.silverscreen.api.api.MovieApi
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.MovieRepository
import edu.cs371m.silverscreen.ui.movies.OneMoviePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieTimesViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var times = MutableLiveData<MoviePost>().apply { value = null }

    fun observeTimes(): LiveData<MoviePost> {
        return times
    }

//    fun netSubRefresh() = viewModelScope.launch(
//        context = viewModelScope.coroutineContext + Dispatchers.IO
//    ) {
//       times.postValue(
//            movieRepo.fetchResponse(
//                "2019-11-28",
//                "78701",
//                "10",
//                "bsj768xkm54t6wuchqxxrbrt"
//            )
//        )
    }



