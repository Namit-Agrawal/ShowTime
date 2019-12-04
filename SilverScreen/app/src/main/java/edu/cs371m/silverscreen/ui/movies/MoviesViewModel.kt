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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.api.api.*
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
    private val movieDbApi = MovieDBApi.create()
    private val movieDBRepo = MovieDBRepository(movieDbApi)

    var movies_all_list = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }
    var recommended_all_list = MutableLiveData<List<MovieDBPost>>().apply {
        value = mutableListOf()
    }

    var rec_search = MutableLiveData<List<MovieDBPost>>().apply {
        value = mutableListOf()
    }


    var movie = MutableLiveData<MoviePost>().apply { value = null }
    var rec_movie = MutableLiveData<String>().apply { value = "Frozen II" }
    var cast = MutableLiveData<MoviePost>().apply { value = null }
    var favorites = MutableLiveData<List<MoviePost>>().apply { value = mutableListOf() }
    var zipcode = MutableLiveData<String>().apply { value = "78705" }
    var radius = MutableLiveData<String>().apply { value = "10" }
    var movieDBId = MutableLiveData<Int>().apply { value = 330457}
    var theatre_all_list = MutableLiveData<List<TheatrePost>>().apply {
        value = mutableListOf()
    }

    var user = MutableLiveData<FirebaseUser>().apply {value = null}

    fun observeUser():LiveData<FirebaseUser?> {
        return user
    }

    fun setUser(newUser: FirebaseUser?) {
        user.postValue(newUser)
    }

//
    fun writeNewUser(
        userID: String, name: String, email: String,
        favs: List<String>, zip: String
    ) {
        val user = User(name, email, zip, favs)
        var database = FirebaseDatabase.getInstance().reference
        database.child("users").child(userID).setValue(user)

        Log.d("entering ", " database")
    }

    fun fetchFavorites(user: FirebaseUser?) {
        val favListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
               // val fav = dataSnapshot.getValue(User::class.java)
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        //postReference.addValueEventListener(postListener)
    }




    data class User(
        var username: String,
        var email: String,
        var zipcode: String,
        var favs: List<String>
    )

    fun observeTheaters():LiveData<List<TheatrePost>> {
        return theatre_all_list
    }
    fun observeMovies(): LiveData<List<MoviePost>> {
        return movies_all_list
    }
    fun observeRecommended(): LiveData<List<MovieDBPost>>{
        return recommended_all_list
    }

    fun observeSearchRecommended(): LiveData<List<MovieDBPost>>{
        return rec_search
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
//        var i = 0
//        var listMoviesID = mutableListOf<String>()
//        while (i<favorites.value!!.size) {
//            listMoviesID.add(localList!![i].id)
//            i++
//        }
//        Log.d("message", user.value.toString() +"user is...")
            //if no users, this will mess up
        //writeNewUser(user.value!!.uid, user.value!!.displayName!!,user.value!!.email!!,listMoviesID, zipcode.value!! )
//
//        var firebaseData = FirebaseDatabase.getInstance().reference
//        firebaseData
//            .child("users")
//            .child(user.value!!.uid)
//            .child("favs")
//            .child(item.id)
//            .setValue(true)

        Log.d("message", favorites.value!!.size.toString() + "size is* ")

    }


    fun removeFav(item: MoviePost)
    {
        val localList = favorites.value?.toMutableList()
        localList?.let {
            it.remove(item)
            favorites.value = it

        }
//        var i = 0
//        var listMoviesID = mutableListOf<String>()
//        while (i<favorites.value!!.size) {
//            listMoviesID.add(localList!![i].id)
//            i++
//        }
//        Log.d("message", user.value.toString() +"user is...")
//        //if no users, this will mess up
//        writeNewUser(user.value!!.uid,user.value!!.displayName!!, user.value!!.email!!, listMoviesID, zipcode.value!! )

//        var firebaseData = FirebaseDatabase.getInstance().reference
//        firebaseData
//            .child("users")
//            .child(user.value!!.uid)
//            .child("favs")
//            .child(item.id)
//            .setValue(null)

    }



    fun observeZip(): LiveData<String>{
        return zipcode
    }

    fun observeAMovieForRec(): LiveData<String>{
        return rec_movie
    }

    fun updateAMovieForRec(str: String)
    {
        rec_movie.postValue(str)
        Log.d("*******************************************************",rec_movie.value!!)
    }
    fun updateZip(str: String)
    {
        zipcode.postValue(str)
    }
    fun observeRadius(): LiveData<String>{
        return radius
    }
    fun observeID(): LiveData<Int>{
        return movieDBId
    }
    fun updateId(i: Int){
        movieDBId.postValue(i)
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

    fun netSubRecommendedRefresh() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {

        recommended_all_list.postValue(
            movieDBRepo.fetchRec(movieDBId.value!!, "f1e47867122912dbf25aa3bfcd06ebcb")
        )
    }

    fun netSubRecommendedSearchRefresh() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        rec_search.postValue(
            movieDBRepo.fetchResponse(rec_movie.value!!, "f1e47867122912dbf25aa3bfcd06ebcb")
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

