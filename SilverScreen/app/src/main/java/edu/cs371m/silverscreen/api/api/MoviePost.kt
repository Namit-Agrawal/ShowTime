package edu.cs371m.silverscreen.api.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class MoviePost(
    @SerializedName("original_title")
    val movieName: String,
    @SerializedName("overview")
    val description:String,
    @SerializedName("id")
    val movie_id:Int,
    @SerializedName("release_date")
    val date:String,
    @SerializedName("popularity")
    val rating: Float,
    @SerializedName("runtime")
    val duration:Int,
    @SerializedName("cast")
    val cast: Array<cast_obj>,
    @SerializedName("poster_path")
    val thumbnail:String
//    val allGenres: List<String>,
//    @SerializedName("longDescription")
//    val description: String,
//    @SerializedName("topCast")
//    val cast: List<String>,
//    @SerializedName("directors")
//    val director: List<String>,
//    @SerializedName("runTime")
//    val duration: String,
//    @SerializedName("ratings")
//    val rating: List<String>
////    @SerializedName("releaseDate")
//    val date: Date
//    @SerializedName("preferredImage")
)


data class cast_obj(
    @SerializedName("name")
    val actor: String
)