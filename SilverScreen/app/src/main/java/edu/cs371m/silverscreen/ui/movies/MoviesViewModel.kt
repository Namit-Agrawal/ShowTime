package edu.cs371m.silverscreen.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.cs371m.silverscreen.api.api.MovieApi
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        //all_list.postValue(movieRepo.fetchResponse(null,null,null))

    }


        private val _text = MutableLiveData<String>().apply {
        value = "This is movies Fragment"
    }
    val text: LiveData<String> = _text
}