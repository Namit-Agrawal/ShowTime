package edu.cs371m.silverscreen.ui.movies;


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.ui.cast.*
import edu.cs371m.silverscreen.ui.movie_times.MovieTimes
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import edu.cs371m.silverscreen.ui.trailer.Trailers
import kotlinx.android.synthetic.main.activity_one_movie_post.*

class OneMoviePost : AppCompatActivity() {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var entireMoviePost: MoviePost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_movie_post)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        val callingBundle = intent.extras
        var movie_title = ""
        var img = ""
        var duration = ""
        callingBundle?.apply {
            entireMoviePost = intent.getParcelableExtra("movie_info")!!
            movie_title = entireMoviePost.movieName
            img = "http://demo.tmsimg.com/"+entireMoviePost.img.image_url
            duration = entireMoviePost.duration.substring(3, 4) + " hr " + entireMoviePost.duration.substring(
                    5,
                    7
                ) + " min"
        }
        viewModel = this.run {
            ViewModelProviders.of(this)[MoviesViewModel::class.java]}

        mov_title.text = movie_title
        duration_lay.text = duration
        viewModel.netFetchImage(img, movie_thumbnail, true)
        movie_trailers.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frag, Trailers.newInstance())
                .commit()
        }
        movie_info.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frag, Cast.newInstance(entireMoviePost))
                .commit()
        }
        movie_timings.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frag, MovieTimes.newInstance(entireMoviePost))
                .commit()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}