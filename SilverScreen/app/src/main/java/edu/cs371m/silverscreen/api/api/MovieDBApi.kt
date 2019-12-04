
package edu.cs371m.silverscreen.api.api
import android.graphics.Movie
import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface MovieDBApi {
    //    @GET("v1.1/movies/showings?startDate=2019-11-17&zip=78701&api_key=bsj768xkm54t6wuchqxxrbrt")
//    @GET("/3/movie/now_playing?api_key=f1e47867122912dbf25aa3bfcd06ebcb&region=US")
//    suspend fun getTopBefore(): MovieResponse
//
//    @GET("/3/movie/{movie_id}?api_key=f1e47867122912dbf25aa3bfcd06ebcb&region=US")
//    suspend fun getMovie(
//        @Path("movie_id")movie_id:Int):MovieR
//    @GET("/3/movie/{movie_id}/credits?api_key=f1e47867122912dbf25aa3bfcd06ebcb&region=US")
//    suspend fun getCredits(
//        @Path("movie_id")movie_id:Int):MovieR

    @GET("/3/search/movie/")
        suspend fun fetchSearchedMovies(
        @Query("api_key")key:String,
        @Query("query")query: String
    ):MovieDBResponse

    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun fetchRecommendedMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String) :MovieDBResponse



    data class MovieDBResponse(val results:List<MovieDBPost>)




    companion object {
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder()
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        //private const val BASE_URL = "https://developer.fandango.com/"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("api.themoviedb.org")
            .build()
        fun create(): MovieDBApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): MovieDBApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                .build()
                .create(MovieDBApi::class.java)
        }
    }
}
@Parcelize
data class MovieDBPost
    (
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
    @SerializedName("poster_path")
    val thumbnail:String

): Parcelable
