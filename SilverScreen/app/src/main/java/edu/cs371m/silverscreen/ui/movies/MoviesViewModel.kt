package edu.cs371m.silverscreen.ui.movies

import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
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

    var movie = MutableLiveData<MoviePost>().apply { value = null }
    var cast = MutableLiveData<MoviePost>().apply { value = null }

    fun observeMovies(): LiveData<List<MoviePost>> {
        return all_list
    }

    fun observeMovieInfo(): LiveData<MoviePost> {
        return movie
    }

    fun observeCast(): LiveData<MoviePost> {
        return cast
    }

    fun netSubRefresh() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        all_list.postValue(
            movieRepo.fetchResponse(
                "2019-12-01",
                "78701",
                "10",
                "7",
                "bsj768xkm54t6wuchqxxrbrt"
            )
        )
    }

    fun netFetchImage(thumbnail: String, imageView: ImageView, bool: Boolean) {
        Log.d("*************8", thumbnail)
        Glide.glideFetch(thumbnail, imageView, bool)
    }

    companion object {
        fun doMoviePost(context: Context, moviePost: MoviePost) {
            val intent = Intent(context, OneMoviePost::class.java)
            val myExtras = Bundle()
            myExtras.putParcelable("movie_info", moviePost)
            intent.putExtras(myExtras)
            startActivity(context, intent, myExtras)
        }

    }
}

