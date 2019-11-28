package edu.cs371m.silverscreen.ui.cast
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import kotlinx.android.synthetic.main.cast_fragment.*

class Cast : Fragment() {

    companion object {
        fun newInstance(moviePost: MoviePost) : Cast{
            val cast = Cast()
            val b = Bundle()
            b.putParcelable("moviepost", moviePost)
            cast.arguments = b
            return cast
        }
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        val post = this.arguments?.getParcelable<MoviePost>("moviepost")
        if(post!=null) {
            if(post.rating!=null)
                rating.text = post.rating[0].actRating
            if(post.director!=null)
                director_list.text = post.getMovieList(post.director)
            if(post.cast!=null)
                cast_list.text = post.getMovieList(post.cast)
            if(post.releaseDate!=null)
                release.text = post.releaseDate
            if(post.description!=null)
                synopsis.text = post.description
            if(post.allGenres!=null)
                genres_list.text = post.getMovieList(post.allGenres)

        }
        // TODO: Use the ViewModel
    }

}