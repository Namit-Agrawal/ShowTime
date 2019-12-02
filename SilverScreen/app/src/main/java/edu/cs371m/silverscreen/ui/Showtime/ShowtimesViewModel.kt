package edu.cs371m.silverscreen.ui.Showtime

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

class ShowtimesViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val movieApi = MovieApi.create()
    private val movieRepo = MovieRepository(movieApi)

    var showtimes = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }

    fun observeTimes(): LiveData<List<MoviePost>> {
        return showtimes
    }
    fun netSubRefresh(theatreId:String) = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        showtimes.postValue(
            movieRepo.fetchShowTimes(theatreId,"2019-12-02", "7", "bsj768xkm54t6wuchqxxrbrt")
            )
    }

    fun netFetchImage(thumbnail: String, imageView: ImageView, bool: Boolean) {
        Log.d("*************8", thumbnail)
        Glide.glideFetch(thumbnail, imageView, bool)
    }



}
