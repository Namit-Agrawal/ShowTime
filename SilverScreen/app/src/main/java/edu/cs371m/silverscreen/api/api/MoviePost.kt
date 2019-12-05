package edu.cs371m.silverscreen.api.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MoviePost(
    @SerializedName("title")
    val movieName: String? = null,
    @SerializedName("genres")
    val allGenres: List<String>? = null,
    @SerializedName("longDescription")
    val description: String? = null,
    @SerializedName("topCast")
    val cast: List<String>? = null,
    @SerializedName("directors")
    val director: List<String>? = null,
    @SerializedName("releaseDate")
    val releaseDate : String? = null,
    @SerializedName("runTime")
    val duration: String? = null,
    @SerializedName("preferredImage")
    val img: image? = null,
    @SerializedName("ratings")
    val rating: List<Rating>? = null,
    @SerializedName("showtimes")
    val showtimes: List<Times>? = null,
    @SerializedName("subType")
    val type: String? = null,
    @SerializedName("rootId")
    val id: String? = null
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
    val actRating: String? = null
): Parcelable

@Parcelize
data class image(
    @SerializedName("uri")
    val image_url: String? = null
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
    val phone: String?,
    @SerializedName("subType")
    val type: String?

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
    val theatre : Theatre? = null,
    @SerializedName("dateTime")
    val time :String? = null
):Parcelable

@Parcelize
data class Theatre(
    @SerializedName("id")
    val theatreId: String? = null,
    @SerializedName("name")
    val name:String? = null
): Parcelable