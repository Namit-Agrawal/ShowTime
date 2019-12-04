package edu.cs371m.silverscreen.api.api

import com.google.gson.Gson
import java.util.*


class MovieDBRepository(private val movieApi: MovieDBApi) {
    suspend fun fetchResponse(query:String, key: String): List<MovieDBPost> {
        return movieApi.fetchSearchedMovies(key, query).results
    }




}

