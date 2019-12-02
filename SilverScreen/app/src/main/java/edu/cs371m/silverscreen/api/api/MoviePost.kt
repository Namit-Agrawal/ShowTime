package edu.cs371m.silverscreen.api.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
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
    @SerializedName("releaseDate")
    val releaseDate : String,
    @SerializedName("runTime")
    val duration: String,
    @SerializedName("preferredImage")
    val img: image,
    @SerializedName("ratings")
    val rating: List<Rating>,
    @SerializedName("showtimes")
    val showtimes: List<Times>
):Parcelable {
    fun getMovieList(list: List<String>): String {
        // XXX Write me
        val sb = StringBuilder()
        var i = 0
        if(list!=null) {
            while (i < list.size) {
                sb.append(list[i])
                sb.append(", ")
                i++
            }
            sb.delete(sb.length - 2, sb.length)
        }
        return sb.toString()
    }
}


@Parcelize
data class Rating(
    @SerializedName("code")
    val actRating: String
): Parcelable
@Parcelize
data class image(
    @SerializedName("uri")
    val image_url: String
): Parcelable





@Parcelize
data class TheatrePost(
    @SerializedName("theatreId")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val loc: location,
    @SerializedName("telephone")
    val phone: String?

):Parcelable

@Parcelize
data class location (
    @SerializedName("distance")
    val distance : Double,
    @SerializedName("address")
    val address: address

):Parcelable

@Parcelize
data class address (
    @SerializedName("street")
    val st: String,
    @SerializedName("city")
    val city: String
):Parcelable




@Parcelize
data class Times(
    @SerializedName("theatre")
    val theatre : Theatre,
    @SerializedName("dateTime")
    val time :String
):Parcelable

@Parcelize
data class Theatre(
    @SerializedName("id")
    val theatreId: String,
    @SerializedName("name")
    val name:String
): Parcelable