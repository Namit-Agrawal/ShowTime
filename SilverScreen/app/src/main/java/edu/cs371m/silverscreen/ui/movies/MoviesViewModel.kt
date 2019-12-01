package edu.cs371m.silverscreen.ui.movies

import android.content.Context
import android.content.Intent
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
import edu.cs371m.silverscreen.api.api.TheatrePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var movies_all_list = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }

    var movie = MutableLiveData<MoviePost>().apply { value = null }
    var cast = MutableLiveData<MoviePost>().apply { value = null }
    var zipcode = MutableLiveData<String>().apply { value = "78705" }
    var theatre_all_list = MutableLiveData<List<TheatrePost>>().apply {
        value = mutableListOf()
    }
    fun observeTheaters():LiveData<List<TheatrePost>> {
        return theatre_all_list
    }


    fun netSubTheatreRefresh()= viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO) {
        theatre_all_list.postValue(movieRepo.fetchTheatre(zipcode.value!!, "10","bsj768xkm54t6wuchqxxrbrt" ))
    }

    fun observeMovies(): LiveData<List<MoviePost>> {
        return movies_all_list
    }
    fun observeZip(): LiveData<String>{
        return zipcode
    }
    fun updateZip(str: String)
    {
        zipcode.postValue(str)
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
                "2019-12-01",
                zipcode.value!!,
                "10",
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

