package edu.cs371m.silverscreen.ui

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel

class FriendProfileViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var movies = MutableLiveData<List<MoviePost>>().apply {
        value = mutableListOf()
    }


    fun observeFriendProfile() :LiveData<List<MoviePost>> {
        return movies
    }

    fun fetchFriendProfile(userUID: String) {
        Log.d("what is profile suppose to be", userUID)
        val database = FirebaseFirestore.getInstance()
        database.collection("Users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("help", "${document.id} => ${document.data}")
                    val map = document.data
                    val name = map.get("username").toString()
                    Log.d("name", name)
                    if (document.id.equals(userUID)) {
                        Log.d("here", "got friend from databasee")

                        val temp = document.toObject(MoviesViewModel.User::class.java)
                        movies.value = temp.entMoviePost
                        //movies.postValue(temp.entMoviePost)
                        Log.d("profile favs", temp.entMoviePost!!.size.toString())


                        //userInfo.update("list", FieldValue.arrayUnion(FirebaseAuth.getInstance().currentUser!!.uid))
                        break
                    }
                }


                //post value
            }
    }

    fun netFetchImage(thumbnail: String, imageView: ImageView, bool: Boolean) {
        Log.d("*************8", thumbnail)
        Glide.glideFetch(thumbnail, imageView, bool)
    }
}