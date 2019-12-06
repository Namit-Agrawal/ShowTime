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
import edu.cs371m.silverscreen.api.api.TheatrePost
import edu.cs371m.silverscreen.ui.movies.OneMoviePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieTimesViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var times = MutableLiveData<MoviePost>().apply { value = null }
    var post = MutableLiveData<TheatrePost>().apply { value = null }

    fun observeTimes(): LiveData<MoviePost> {
        return times
    }
}






