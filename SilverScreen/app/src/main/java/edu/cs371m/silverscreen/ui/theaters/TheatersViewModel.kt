package edu.cs371m.silverscreen.ui.theaters

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

class TheatersViewModel : ViewModel() {
    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var all_list = MutableLiveData<List<TheatrePost>>().apply {
        value = mutableListOf()
    }
    fun observeTheaters():LiveData<List<TheatrePost>> {
        return all_list
    }


    fun netSubRefresh()= viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO) {
        all_list.postValue(movieRepo.fetchTheatre("78705", "10","bsj768xkm54t6wuchqxxrbrt" ))
    }


    companion object {
        fun doTheatrePost(context: Context, post: TheatrePost) {
            val intent = Intent(context, OneTheatrePost::class.java)
            val myExtras = Bundle()
            myExtras.putParcelable("theater_info", post)
            intent.putExtras(myExtras)
            ContextCompat.startActivity(context, intent, myExtras)
        }

    }

}