package edu.cs371m.silverscreen.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun observeMovies():LiveData<List<MoviePost>> {
        return all_list
    }

    fun netSubRefresh()= viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO) {


        val date = Date()

//        val formatter =SimpleDateFormat("yyyy mm dd")
//        //val date2 = date.format(formatter)
//        val date2 = formatter.format(date)
//        val date3 = Date(date2)
     //   Log.d("message", date3.toString() + "************88")
        //date.toString() use date to make the toDate

        //val toDate = "20191117"
//        val toDate = ""
        //val formater = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(toDate)


        all_list.postValue(movieRepo.fetchResponse("2019-11-17","78705","10", "bsj768xkm54t6wuchqxxrbrt"))
    }


        private val _text = MutableLiveData<String>().apply {
        value = "This is movies Fragment"
    }
    val text: LiveData<String> = _text
}