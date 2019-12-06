package edu.cs371m.silverscreen.api.api


import com.google.gson.Gson
import java.util.*


class MovieDBRepository(private val movieApi: MovieDBApi) {
    suspend fun fetchResponse(query:String, key: String): List<MovieDBPost> {
        return movieApi.fetchSearchedMovies(key, query).results
    }

    suspend fun fetchRec(id: Int, key: String): List<MovieDBPost>{
        return movieApi.fetchRecommendedMovies(id, key).results
    }

    suspend fun fetchTrailer(id: Int, key: String): List<videoPost>{
        return movieApi.fetchVideos(id, key).results
    }

    suspend fun fetchComingSoon(key: String, reg: String): List<MovieDBPost>{
        return movieApi.fetchUpcoming(key, reg).results
    }


}

