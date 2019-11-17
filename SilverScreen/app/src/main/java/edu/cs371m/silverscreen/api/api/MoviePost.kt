package edu.cs371m.silverscreen.api.api

import com.google.gson.annotations.SerializedName

data class MoviePost(
    @SerializedName("title")
    val movieName: String,
    @SerializedName("genres")
    val allGenres: List<String>,
    @SerializedName("longDescription")
    val description: String,
    @SerializedName("topCast")
    val cast: List<String>,
    @SerializedName("directors")
    val director: List<String>,
    @SerializedName("runTime")
    val duration: String,
    @SerializedName("ratings")
    val rating: List<String>
//    @SerializedName("preferredImage")


)