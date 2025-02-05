package edu.cs371m.silverscreen.api.api
import com.google.gson.GsonBuilder
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
import java.util.concurrent.TimeUnit

interface MovieApi {
@GET("v1.1/movies/showings")



suspend fun getTopBefore(
    @Query("startDate")startDate: String,
    @Query("zip") zip :String,
    @Query("radius") radius: String,
    @Query("numDays") days: String,
    @Query("api_key")key: String): List<MoviePost>


    @GET("v1.1/theatres")
suspend fun getTheatres(
    //can do zip code or lat long
    @Query("zip") zip:String,
    @Query("radius") radius: String,
    @Query("api_key") key: String) : List<TheatrePost>


@GET("v1.1/theatres/{theatreID}/showings")
suspend fun getTimes(
    @Path("theatreID") string: String,
    @Query("startDate")startDate: String,
    @Query("numDays")numDays: String,
    @Query("api_key")key:String
):List<MoviePost>



    data class MovieResponse(val results: List<MoviePost>)

    companion object {
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder()
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        //private const val BASE_URL = "https://developer.fandango.com/"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("data.tmsapi.com")
            .build()
        fun create(): MovieApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): MovieApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(30, TimeUnit.SECONDS) // write timeout
                .readTimeout(30, TimeUnit.SECONDS) // read timeout
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                .build()
                .create(MovieApi::class.java)
        }
    }
}