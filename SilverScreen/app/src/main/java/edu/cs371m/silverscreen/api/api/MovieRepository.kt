package edu.cs371m.silverscreen.api.api

import com.google.gson.Gson
import java.util.*


class MovieRepository(private val movieApi: MovieApi) {
    suspend fun fetchResponse(date:String, zip: String, radius: String, days: String, key: String): List<MoviePost> {
        return movieApi.getTopBefore(date, zip, radius, days, key)
    }

    suspend fun fetchTheatre(zip:String, radius: String, key: String): List<TheatrePost> {
        return movieApi.getTheatres(zip,radius, key)
    }
    suspend fun fetchShowTimes(theatreID: String, date: String, numDays: String, key: String): List<MoviePost> {
        return movieApi.getTimes(theatreID, date, numDays, key)
    }


}

