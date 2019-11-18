package edu.cs371m.silverscreen.api.api

import com.google.gson.Gson
import java.util.*


class MovieRepository(private val movieApi: MovieApi) {
    suspend fun fetchResponse(): List<MoviePost> {
        return movieApi.getTopBefore().results
    }

    suspend fun fetchMovie(id: Int):MoviePost {
        return movieApi.getMovie(id).results
    }

    suspend fun fetchCredits(id: Int): MoviePost {
        return movieApi.getCredits(id).results
    }
}