package edu.cs371m.silverscreen.api.api
import android.graphics.Movie
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

interface MovieApi {
//    @GET("v1.1/movies/showings?startDate=2019-11-17&zip=78701&api_key=bsj768xkm54t6wuchqxxrbrt")
    @GET("v1.1/movies/showings")
    fun getTopBefore(
    @Query("startDate")startDate: Date,
    @Query("zip") zip :String,
    @Query("radius") radius: String
    ):MovieResponse

    data class MovieResponse(val results: List<MoviePost>)



    companion object {
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder()
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        //private const val BASE_URL = "https://developer.fandango.com/"
        var httpurl = HttpUrl.Builder()
            .scheme("http")
            .host("data.tmsapi.com")
            .build()
        fun create(): MovieApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): MovieApi {

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
                .create(MovieApi::class.java)
        }
    }
}