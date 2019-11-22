package edu.cs371m.silverscreen.ui.movies

import android.graphics.Movie
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.api.api.MovieApi
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MoviesViewModel : ViewModel() {
    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var all_list = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }

    var movie = MutableLiveData<MoviePost>().apply { value =null }
    var cast = MutableLiveData<MoviePost>().apply { value =null }

    fun observeMovies():LiveData<List<MoviePost>> {
        return all_list
    }

    fun observeMovieInfo():LiveData<MoviePost> {
        return movie
    }

    fun observeCast():LiveData<MoviePost> {
        return cast
    }

    fun netSubRefresh()= viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO) {
        all_list.postValue(movieRepo.fetchResponse("2019-11-22","78705","10", "2w6p3khxsxqkgjkjtxcr4xjb"))
    }

    fun netFetchImage(thumbnail: String, imageView: ImageView) {
        Log.d("*************8", thumbnail)

        Glide.glideFetch(thumbnail, imageView)
    }



        private val _text = MutableLiveData<String>().apply {
        value = "This is movies Fragment"
    }
    val text: LiveData<String> = _text
}