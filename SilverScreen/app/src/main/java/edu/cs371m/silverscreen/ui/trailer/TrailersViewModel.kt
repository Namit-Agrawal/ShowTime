package edu.cs371m.silverscreen.ui.trailer

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.api.api.*
import edu.cs371m.silverscreen.ui.movies.OneMoviePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrailersViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val movieDBApi = MovieDBApi.create()
    private val movieRepo = MovieDBRepository(movieDBApi)
    val queryResult= MutableLiveData<List<MovieDBPost>>().apply {
        value = null
    }

    val movieID = MutableLiveData<Int>().apply {
        value = null
    }

    val videoList= MutableLiveData<List<videoPost>>().apply {
        value = null
    }

    //query movies from db
    fun DBQuery(item : MoviePost) = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        queryResult.postValue(
            movieRepo.fetchResponse(item.movieName!!, "f1e47867122912dbf25aa3bfcd06ebcb")
        )
    }

    fun DBVideoQuery() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        videoList.postValue(
            movieRepo.fetchTrailer(movieID.value!!, "f1e47867122912dbf25aa3bfcd06ebcb")
        )
    }

    fun setMovieID(id:Int) {
        movieID.value = id
    }
    fun netFetchImage(thumbnail: String, imageView: ImageView, bool: Boolean) {
        Log.d("*************8", thumbnail)
        Glide.glideFetch(thumbnail, imageView, bool)
    }

    companion object {
        fun doVideoPost(context: Context, youtube_key: String) {

           // val intent = Intent(context, VideoPost::class.java)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtube_key))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + youtube_key))

            try {
                context.startActivity(intent)
            }catch (e : ActivityNotFoundException){
                context.startActivity(webIntent)
            }

          //  intent.putExtras(myExtras)
          //  ContextCompat.startActivity(context, intent, myExtras)
        }


    }

}