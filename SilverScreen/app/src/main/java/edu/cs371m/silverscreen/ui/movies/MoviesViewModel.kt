package edu.cs371m.silverscreen.ui.movies

import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.api.api.MovieApi
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.MovieRepository
import edu.cs371m.silverscreen.api.api.TheatrePost
import edu.cs371m.silverscreen.ui.theaters.OneTheatrePost
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

    var movies_all_list = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }

    var movie = MutableLiveData<MoviePost>().apply { value = null }
    var cast = MutableLiveData<MoviePost>().apply { value = null }
    var favorites = MutableLiveData<List<MoviePost>>().apply { value = mutableListOf() }
    var zipcode = MutableLiveData<String>().apply { value = "78705" }
    var radius = MutableLiveData<String>().apply { value = "10" }
    var theatre_all_list = MutableLiveData<List<TheatrePost>>().apply {
        value = mutableListOf()
    }
    fun observeTheaters():LiveData<List<TheatrePost>> {
        return theatre_all_list
    }
    fun observeMovies(): LiveData<List<MoviePost>> {
        return movies_all_list
    }
    fun observeFavorites(): LiveData<List<MoviePost>>{
        return favorites
    }
    fun isFav(item: MoviePost): Boolean
    {
        Log.d("mess","fek;lerferg")

        if (favorites.value == null) {
            return false
        }
        Log.d("message", favorites.value!!.size.toString() + "size is ")
        return favorites.value!!.contains(item)
    }
    fun addFav(item: MoviePost)
    {
        val localList = favorites.value?.toMutableList()
        localList?.let {
            it.add(item)
            favorites.value = it

        }
        Log.d("message", favorites.value!!.size.toString() + "size is* ")

    }
    fun removeFav(item: MoviePost)
    {
        val localList = favorites.value?.toMutableList()
        localList?.let {
            it.remove(item)
            favorites.value = it

        }
    }



    fun observeZip(): LiveData<String>{
        return zipcode
    }
    fun updateZip(str: String)
    {
        zipcode.postValue(str)
    }
    fun observeRadius(): LiveData<String>{
        return radius
    }
    fun updateRadius(str: String)
    {
        radius.postValue(str)
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
        movies_all_list.postValue(
            movieRepo.fetchResponse(
                "2019-12-03",
                zipcode.value!!,
                radius.value!!,
                "7",
                "bsj768xkm54t6wuchqxxrbrt"
            )
        )
    }

    fun netSubTheatreRefresh()= viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO) {
        theatre_all_list.postValue(movieRepo.fetchTheatre(zipcode.value!!, radius.value!!,"bsj768xkm54t6wuchqxxrbrt" ))
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
        fun doTheatrePost(context: Context, post: TheatrePost) {
            val intent = Intent(context, OneTheatrePost::class.java)
            val myExtras = Bundle()
            Log.d("mesage in Theatre Post check if null", post.toString())
            myExtras.putParcelable("theatre_info", post)
            intent.putExtras(myExtras)
            startActivity(context, intent, myExtras)
        }

    }
}

